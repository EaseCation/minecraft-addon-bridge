# Nukkit Addon Bridge 架构迁移计划

**从纯 Java DTO 方案迁移到 Java + JavaScript 混合运行时架构**

---

## 📋 文档元信息

| 项目 | 内容 |
|------|------|
| **文档版本** | 1.0.0 |
| **创建日期** | 2025-01-08 |
| **目标版本** | v0.2.0 |
| **预计工期** | 6-8 周 |
| **优先级** | 高 |

---

## 1. 背景与动机

### 1.1 当前架构问题

#### 核心痛点

| 问题类别 | 具体表现 | 影响程度 |
|---------|---------|---------|
| **代码膨胀** | 生成了 10,690 个 Java 文件，8 个版本 × 17+ 模块 | 🔴 高 |
| **类型系统不匹配** | JSON Schema 的 anyOf/oneOf 需要复杂的 sealed interface + 自定义 Deserializer | 🔴 高 |
| **维护成本高** | 每次 Schema 更新需重新生成大量代码，手动编写 Upgrader | 🟠 中 |
| **编译时间长** | 首次编译需要处理大量文件，IDE 性能受影响 | 🟡 中 |
| **版本升级复杂** | 跨版本字段映射需人工实现，难以测试和调试 | 🔴 高 |

#### 典型案例：MaxStackSize 的多态处理

```java
// 当前方案：需要生成复杂的 sealed interface
@JsonDeserialize(using = MaxStackSize.Deserializer.class)
public sealed interface MaxStackSize {
    record MaxStackSize_Variant0(@JsonValue Double value) implements MaxStackSize {}
    record MaxStackSize_Variant1(@JsonProperty("value") Double value) implements MaxStackSize {}

    class Deserializer extends JsonDeserializer<MaxStackSize> {
        // 30+ 行自定义反序列化逻辑
    }
}
```

**问题**：
- 为简单的 `1 | {value: 1}` 类型生成了大量代码
- 用户在 Java 层还需要模式匹配处理不同变体
- Schema 稍有变化就需要重新生成

### 1.2 新架构优势

#### 核心理念：职责分离

```
┌─────────────────────────────────────────────────────────────┐
│                        旧架构                                │
│  JSON Schema → Java DTO → Jackson 解析 → 版本升级 → 业务层  │
│  （全部在 Java 中，类型系统不匹配，代码膨胀）                │
└─────────────────────────────────────────────────────────────┘

                            ⬇️

┌─────────────────────────────────────────────────────────────┐
│                        新架构                                │
│                                                              │
│  ┌─────────────────  JS 运行时层  ─────────────────┐        │
│  │ JSON Schema → TS Types → 解析 → 版本升级 →     │        │
│  │ 标准化转换 → 输出简化 JSON                      │        │
│  └───────────────────────┬─────────────────────────┘        │
│                          ⬇️                                  │
│  ┌─────────────────  Java 业务层  ─────────────────┐        │
│  │ 接收标准化 JSON → 简单 DTO → 业务逻辑          │        │
│  └─────────────────────────────────────────────────┘        │
│                                                              │
│  ✅ JS 处理复杂解析逻辑（动态类型，天然支持 anyOf）        │
│  ✅ Java 只处理标准化数据（静态类型，IDE 友好）            │
└─────────────────────────────────────────────────────────────┘
```

#### 关键优势

| 优势 | 说明 | 收益 |
|------|------|------|
| **代码量减少 90%+** | Java 只保留一套简化 DTO（最新版标准化格式） | 编译快、IDE 流畅、维护简单 |
| **版本升级灵活** | TypeScript 编写升级逻辑，利用 JS 生态工具 | 易测试、易调试、易扩展 |
| **Schema 变更友好** | JS 层适配变化，Java DTO 保持稳定 | 减少重新编译、向下兼容 |
| **开发体验优化** | Java 开发者只需关注业务逻辑，不关心 Schema 细节 | 降低心智负担、提高效率 |
| **自动化增强** | 利用 TS 生态（Ajv、json-schema-to-typescript）自动生成解析器 | 减少人工维护 |

---

## 2. 目标架构设计

### 2.1 整体架构图

```
┌──────────────────────────────────────────────────────────────────┐
│                          用户文件系统                             │
│  behavior_packs/my_addon/                                        │
│    ├── blocks/custom_block.json      (format_version: 1.19.40)  │
│    ├── items/custom_item.json        (format_version: 1.21.60)  │
│    └── entities/custom_entity.json   (format_version: 1.20.10)  │
└────────────────────────────┬─────────────────────────────────────┘
                             ⬇️
┌──────────────────────────────────────────────────────────────────┐
│                    Java 层：文件扫描与读取                        │
│  AddonLoader.java                                                │
│    └── scanFiles() → List<JsonFile> {path, content}             │
└────────────────────────────┬─────────────────────────────────────┘
                             ⬇️ 传递文件列表和内容
┌──────────────────────────────────────────────────────────────────┐
│                    JS 运行时层：解析与转换                        │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │  AddonParser.ts (主入口)                                   │ │
│  │    ├── 1. 识别文件类型和版本                               │ │
│  │    ├── 2. 加载对应版本的 TS Types                          │ │
│  │    ├── 3. 使用 Ajv 验证 JSON Schema                        │ │
│  │    ├── 4. 执行版本升级链 (1.19.40 → ... → 1.21.60)         │ │
│  │    └── 5. 转换为标准化格式                                 │ │
│  └────────────────────────────────────────────────────────────┘ │
│                                                                  │
│  VersionUpgrader.ts                                              │
│    ├── upgradeBlock_v1_19_40_to_v1_20_10()                      │
│    ├── upgradeBlock_v1_20_10_to_v1_21_60()                      │
│    └── ... (其他模块和版本)                                      │
│                                                                  │
│  StandardizedConverter.ts                                        │
│    ├── convertBlockToStandard()   → StandardBlock               │
│    ├── convertItemToStandard()    → StandardItem                │
│    └── convertEntityToStandard()  → StandardEntity              │
└────────────────────────────┬─────────────────────────────────────┘
                             ⬇️ 返回标准化 JSON
┌──────────────────────────────────────────────────────────────────┐
│                    Java 层：DTO 解析与业务逻辑                    │
│  StandardBlock.java (简化 DTO)                                   │
│    ├── identifier: String                                        │
│    ├── displayName: String                                       │
│    ├── hardness: float                                           │
│    ├── lightEmission: float      // 统一为 0.0-1.0              │
│    └── components: Map<String, Object>  // 扁平化组件           │
│                                                                  │
│  ServerAdapter.register(StandardBlock)                           │
│    └── 注册到 Nukkit/PNX/MOT 等服务器                            │
└──────────────────────────────────────────────────────────────────┘
```

### 2.2 数据流设计

#### 阶段 1：Java → JS（传递原始数据）

```java
// Java 侧：扫描并读取所有 JSON 文件
public class AddonLoader {
    public List<ParsedAddon> loadAddon(Path addonPath) {
        List<JsonFile> files = scanFiles(addonPath);

        // 调用 JS 引擎
        String resultJson = jsRuntime.call("parseAddon", files);

        // 反序列化为简化 DTO
        return objectMapper.readValue(resultJson,
            new TypeReference<List<StandardBlock>>() {});
    }
}
```

#### 阶段 2：JS 内部处理（解析 + 升级 + 转换）

```typescript
// JS 侧：主入口函数
export function parseAddon(files: JsonFile[]): StandardAddon {
  const results = {
    blocks: [],
    items: [],
    entities: []
  };

  for (const file of files) {
    const type = detectType(file.path);  // 识别类型：block/item/entity
    const version = extractVersion(file.content);  // 提取 format_version

    // TODO: 加载对应版本的 Schema 和 Types
    // TODO: 使用 Ajv 验证 JSON

    // 执行版本升级
    const upgraded = upgradeToLatest(file.content, type, version);

    // 转换为标准化格式
    const standardized = convertToStandard(upgraded, type);

    results[type + 's'].push(standardized);
  }

  return results;
}
```

#### 阶段 3：JS → Java（返回标准化数据）

```typescript
// 标准化输出格式（简化、扁平化）
interface StandardBlock {
  identifier: string;           // 必需字段
  displayName?: string;
  hardness: number;              // 统一为 float
  lightEmission: number;         // 统一为 0.0-1.0
  friction: number;

  // 扁平化的组件（不再区分版本）
  components: {
    isWaterloggable?: boolean;
    craftingTable?: string;
    geometry?: string;
    // ... 其他组件（扁平化，移除嵌套）
  };

  // 元数据（用于调试和日志）
  metadata: {
    sourceVersion: string;       // 原始版本
    upgradePath: string[];       // 升级路径
    warnings: string[];          // 转换警告
  };
}
```

### 2.3 职责划分

| 层级 | 职责 | 技术栈 | 输出 |
|------|------|--------|------|
| **Java 文件扫描层** | 遍历 addon 目录，读取 JSON 文件内容 | Java NIO | `List<JsonFile>` |
| **JS 解析层** | 识别类型、验证 Schema、解析 JSON | TypeScript + Ajv | 已解析的对象 |
| **JS 升级层** | 执行版本升级链，处理字段变更 | TypeScript | 最新版本对象 |
| **JS 转换层** | 转换为标准化格式，扁平化结构 | TypeScript | `StandardBlock/Item/Entity` |
| **Java DTO 层** | 反序列化标准化 JSON | Jackson + 简化 DTO | Java 对象 |
| **Java 业务层** | 注册到服务器，执行游戏逻辑 | Nukkit API | 运行时效果 |

---

## 3. 核心设计决策

### 3.1 JS 引擎选型

#### 候选方案对比

| 引擎 | 优点 | 缺点 | 推荐度 |
|------|------|------|--------|
| **GraalVM JS** | 🟢 性能最佳<br>🟢 完整 ES2023 支持<br>🟢 与 Java 互操作性强 | 🔴 包体积大（~50MB）<br>🟠 启动较慢 | ⭐⭐⭐⭐⭐ |
| **Nashorn** | 🟢 JDK 内置（JDK 8-14）<br>🟢 轻量级 | 🔴 已弃用<br>🔴 ES5 支持，无法使用 TS | ⭐⭐ |
| **Rhino** | 🟢 轻量级<br>🟢 成熟稳定 | 🟠 ES6 支持有限<br>🟠 性能一般 | ⭐⭐⭐ |
| **QuickJS (通过 JNI)** | 🟢 极轻量（~1MB）<br>🟢 ES2020 支持 | 🔴 需要 JNI 桥接<br>🟠 社区较小 | ⭐⭐⭐ |

#### 最终决策：GraalVM JS

**理由**：
1. **性能**：JIT 编译，接近 V8 性能
2. **兼容性**：完整支持 ES2023，可直接运行 TS 编译后的代码
3. **互操作性**：`Value.asHostObject()` 可直接传递 Java 对象
4. **生态**：支持 npm 模块（通过 Babel 转译）

**依赖添加**：
```gradle
dependencies {
    implementation("org.graalvm.js:js:23.1.0")
    implementation("org.graalvm.js:js-scriptengine:23.1.0")
}
```

### 3.2 标准化 DTO 设计原则

#### 原则 1：扁平化优先

```typescript
// ❌ 避免：深层嵌套
interface OldBlock {
  minecraft:block: {
    description: {
      identifier: string;
      properties: { ... };
    };
    components: {
      "minecraft:light_emission": {
        emission: number;
      };
    };
  };
}

// ✅ 推荐：扁平化
interface StandardBlock {
  identifier: string;           // 直接提取
  lightEmission: number;         // 扁平化组件
  properties: Record<string, any>;  // 统一格式
}
```

#### 原则 2：移除多态类型

```typescript
// ❌ 避免：anyOf/oneOf（保留在 JS 层处理）
type MaxStackSize = number | { value: number };

// ✅ 推荐：统一为单一类型
interface StandardItem {
  maxStackSize: number;  // JS 层已归一化为 number
}
```

#### 原则 3：保留元数据用于调试

```typescript
interface StandardBlock {
  // ... 业务字段 ...

  metadata: {
    sourceVersion: string;       // "1.19.40"
    upgradePath: string[];       // ["1.19.40", "1.20.10", "1.21.60"]
    warnings: string[];          // ["Field 'light_emission' converted to 'block_light_emission'"]
    sourceFile: string;          // "blocks/custom_block.json"
  };
}
```

### 3.3 版本升级策略

#### 线性升级设计

```typescript
// VersionUpgrader.ts
export class BlockUpgrader {
  // 版本序列（按时间顺序）
  private static readonly VERSION_SEQUENCE = [
    '1.19.0', '1.19.40', '1.19.50', '1.20.10',
    '1.20.41', '1.20.81', '1.21.50', '1.21.60'
  ];

  // 升级器映射表（版本 → 升级函数）
  private static upgraders = new Map<string, UpgradeFunction>([
    ['1.19.0', upgradeBlock_v1_19_0_to_v1_19_40],
    ['1.19.40', upgradeBlock_v1_19_40_to_v1_19_50],
    ['1.19.50', upgradeBlock_v1_19_50_to_v1_20_10],
    ['1.20.10', upgradeBlock_v1_20_10_to_v1_20_41],
    // ... 其他版本
  ]);

  /**
   * 线性升级到最新版本
   * 注意：始终按版本序列顺序升级，即使某些版本无变化也会经过
   * 这样设计是因为开服时只运行一次，性能要求不高，但逻辑简单可靠
   */
  public static upgradeToLatest(data: any, fromVersion: string): any {
    const startIndex = this.VERSION_SEQUENCE.indexOf(fromVersion);
    let current = data;

    // 从起始版本线性升级到最新版本
    for (let i = startIndex; i < this.VERSION_SEQUENCE.length - 1; i++) {
      const upgrader = this.upgraders.get(this.VERSION_SEQUENCE[i]);
      if (upgrader) {
        current = upgrader(current);
      }
      // 如果某版本无升级器，说明无变化，直接跳过
    }

    return current;
  }
}
```

#### 升级器示例（Block 光照字段）

```typescript
// 1.19.40 → 1.20.10：光照字段类型变更
// minecraft:light_emission: int (0-15) → minecraft:block_light_emission: float (0.0-1.0)

function upgradeBlock_v1_19_40_to_v1_20_10(data: Block_v1_19_40): Block_v1_20_10 {
  const components = { ...data.components };

  // TODO: 检查是否存在旧字段
  if (components['minecraft:light_emission'] !== undefined) {
    const oldValue = components['minecraft:light_emission'];
    // TODO: 转换公式：int / 15.0 = float
    components['minecraft:block_light_emission'] = oldValue / 15.0;
    delete components['minecraft:light_emission'];
  }

  // TODO: 处理其他字段变更...

  return {
    format_version: '1.20.10',
    'minecraft:block': {
      description: data['minecraft:block'].description,
      components
    }
  };
}
```

---

## 4. 实施路线图

### 4.0 schema-types-ts 现状分析

基于深入调研，schema-types-ts 已经具备以下能力：

| 特性 | 状态 | 说明 |
|------|------|------|
| **类型生成** | ✅ 已完成 | 6,379 个 .d.ts 文件，39 MB |
| **版本覆盖** | ✅ 已完成 | 8 个版本（1.19.0 - 1.21.60） |
| **模块支持** | ✅ 已完成 | behavior + resource，17+ 模块 |
| **anyOf/oneOf 处理** | ✅ 已完成 | 生成 Union 类型 |
| **JSDoc 注释** | ⚠️ 部分完成 | 11,017 个字段 UNDOCUMENTED |
| **自动化流程** | ✅ 已完成 | npm scripts + Git 自动切换 |

**现有目录结构**：
```
schema-types-ts/types/
├── behavior/
│   ├── blocks/
│   │   ├── v1_19_0/     # 29 个 .d.ts
│   │   ├── v1_19_40/
│   │   ├── v1_19_50/
│   │   ├── v1_20_10/
│   │   ├── v1_20_41/
│   │   ├── v1_20_81/
│   │   ├── v1_21_50/
│   │   └── v1_21_60/    # 最新版
│   ├── items/
│   │   └── v1_*_*/
│   └── entities/
│       └── v1_*_*/
└── resource/
```

**关键优势**：
- ✅ 已有完整的类型定义，可直接用于 JS 运行时
- ✅ 版本隔离清晰，支持多版本并存
- ✅ 自动化工具完善，可快速更新

**需要补充的工作**：
- ⚠️ 编译 TS → JS（用于 GraalVM 运行）
- ⚠️ 编写版本升级器（基于类型定义）
- ⚠️ 实现标准化转换器

---

### 4.1 分阶段迁移概览

```
Phase 1: 基础设施搭建（2 周）⏱️ 10 工作日
   ├── 1.1 创建 js-runtime 模块结构              [2天]
   ├── 1.2 集成 GraalVM JS 引擎                  [2天]
   ├── 1.3 实现 Java ↔ JS 互操作层               [2天]
   ├── 1.4 配置 TypeScript 编译环境              [1天]
   ├── 1.5 集成 schema-types-ts 生成的类型       [2天]
   └── 1.6 编写基础单元测试                      [1天]

Phase 2: Block 模块试点（2 周）⏱️ 10 工作日
   ├── 2.1 设计 StandardBlock DTO               [1天]
   ├── 2.2 实现 Block 解析器（单版本）           [2天]
   ├── 2.3 实现 Block 版本升级器（3-4 个版本）   [4天]
   ├── 2.4 实现 Block 标准化转换器               [2天]
   └── 2.5 集成测试与调试                        [1天]

Phase 3: Item/Entity 模块扩展（2 周）⏱️ 10 工作日
   ├── 3.1 设计 StandardItem/Entity DTO          [1天]
   ├── 3.2 实现 Item 解析和升级器                [3天]
   ├── 3.3 实现 Entity 解析和升级器              [4天]
   ├── 3.4 统一标准化转换逻辑                    [1天]
   └── 3.5 跨模块集成测试                        [1天]

Phase 4: 自动化工具链完善（1 周）⏱️ 5 工作日
   ├── 4.1 Schema Diff 工具实现                  [2天]
   ├── 4.2 升级器骨架自动生成                    [2天]
   └── 4.3 单元测试覆盖 80%+                     [1天]

Phase 5: 性能优化与文档（1 周）⏱️ 5 工作日
   ├── 5.1 JS 引擎预热和缓存机制                 [2天]
   ├── 5.2 性能基准测试与优化                    [2天]
   └── 5.3 编写开发者指南和迁移文档              [1天]
```

**总计**: 6-8 周（40 工作日）

---

### 4.2 Phase 1: 基础设施搭建（详细任务）

#### 任务 1.1：创建 js-runtime 模块结构 [2天]

**目标**：搭建独立的 JS 运行时模块骨架

**目录结构**：
```
js-runtime/
├── package.json                    # npm 配置
├── tsconfig.json                   # TypeScript 配置
├── build.gradle.kts                # Gradle 配置
├── src/
│   ├── main/
│   │   ├── typescript/             # TS 源码
│   │   │   ├── index.ts           # 主入口（导出给 Java）
│   │   │   ├── parser/
│   │   │   │   ├── AddonParser.ts
│   │   │   │   ├── BlockParser.ts
│   │   │   │   ├── ItemParser.ts
│   │   │   │   └── EntityParser.ts
│   │   │   ├── upgrader/
│   │   │   │   ├── VersionUpgrader.ts
│   │   │   │   ├── BlockUpgrader.ts
│   │   │   │   ├── ItemUpgrader.ts
│   │   │   │   └── EntityUpgrader.ts
│   │   │   ├── converter/
│   │   │   │   ├── StandardConverter.ts
│   │   │   │   ├── BlockConverter.ts
│   │   │   │   ├── ItemConverter.ts
│   │   │   │   └── EntityConverter.ts
│   │   │   ├── types/              # schema-types-ts 输出
│   │   │   │   ├── behavior/
│   │   │   │   └── resource/
│   │   │   └── utils/
│   │   │       ├── version.ts
│   │   │       └── logger.ts
│   │   └── resources/
│   │       └── bundle.js           # 编译后的 JS（给 GraalVM）
│   └── test/
│       └── typescript/
│           └── __tests__/
└── README.md
```

**package.json**：
```json
{
  "name": "@nukkit-addon-bridge/js-runtime",
  "version": "0.2.0",
  "scripts": {
    "build": "tsc && webpack",
    "dev": "tsc --watch",
    "test": "jest",
    "lint": "eslint src/**/*.ts"
  },
  "dependencies": {
    "ajv": "^8.12.0",
    "ajv-formats": "^2.1.1"
  },
  "devDependencies": {
    "@types/node": "^20.0.0",
    "typescript": "^5.6.0",
    "webpack": "^5.90.0",
    "ts-loader": "^9.5.0",
    "jest": "^29.7.0"
  }
}
```

**验收标准**：
- [ ] 目录结构完整，符合模块化设计
- [ ] package.json 配置正确，依赖安装成功
- [ ] tsconfig.json 配置合理（target: ES2020, module: ESNext）
- [ ] Gradle 能识别并管理该模块

---

#### 任务 1.2：集成 GraalVM JS 引擎 [2天]

**文件**：`addon-bridge-core/src/main/java/.../runtime/JSRuntime.java`

**完整实现**：
```java
package net.easecation.bridge.core.runtime;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import java.nio.file.Path;

public class JSRuntime implements AutoCloseable {
    private final Context context;
    private final Value parseAddonFunction;

    public JSRuntime(Path bundlePath) {
        // TODO: 初始化 GraalVM Context，配置沙箱权限
        this.context = Context.newBuilder("js")
            .allowExperimentalOptions(true)
            .option("js.esm-eval-returns-exports", "true")
            // 沙箱配置：禁用危险操作
            .allowIO(false)              // 禁用文件 IO
            .allowNativeAccess(false)    // 禁用 JNI
            .allowPolyglotAccess(org.graalvm.polyglot.PolyglotAccess.NONE)
            .build();

        // TODO: 加载编译后的 JS bundle
        Source source = Source.newBuilder("js", bundlePath.toFile()).build();
        this.context.eval(source);

        // TODO: 获取主入口函数
        this.parseAddonFunction = context.getBindings("js")
            .getMember("parseAddon");

        if (parseAddonFunction == null || !parseAddonFunction.canExecute()) {
            throw new RuntimeException("parseAddon function not found");
        }
    }

    /**
     * 调用 JS 解析 Addon
     * @param filesJson JSON 字符串（文件列表）
     * @return 标准化 JSON 字符串
     */
    public String parseAddon(String filesJson) {
        // TODO: 调用 JS 函数
        Value result = parseAddonFunction.execute(filesJson);

        // TODO: 转换返回值为 Java String
        if (!result.isString()) {
            throw new RuntimeException("Expected string result from parseAddon");
        }

        return result.asString();
    }

    /**
     * 预热 JS 引擎（可选优化）
     */
    public void warmup() {
        // TODO: 执行一次空解析，触发 JIT 编译
        String dummyInput = "[]";
        parseAddon(dummyInput);
    }

    @Override
    public void close() {
        if (context != null) {
            context.close();
        }
    }
}
```

**Gradle 依赖**：
```gradle
// addon-bridge-core/build.gradle.kts
dependencies {
    implementation("org.graalvm.polyglot:polyglot:23.1.2")
    implementation("org.graalvm.polyglot:js:23.1.2")
}
```

**验收标准**：
- [ ] GraalVM Context 成功初始化
- [ ] 能加载并执行简单的 JS 代码（如 `1 + 1`）
- [ ] 沙箱权限配置正确（禁用 IO/网络）
- [ ] 异常处理完善，有详细日志

---

#### 任务 1.3：实现 Java ↔ JS 互操作层 [2天]

**文件 1**：`addon-bridge-core/.../runtime/JsonFile.java`
```java
package net.easecation.bridge.core.runtime;

/**
 * 表示一个待解析的 JSON 文件
 */
public record JsonFile(
    String path,        // 相对路径，如 "blocks/custom_block.json"
    String content      // 文件内容
) {
    public static JsonFile of(Path basePath, Path filePath) {
        // TODO: 读取文件内容
        String relativePath = basePath.relativize(filePath).toString();
        String content = Files.readString(filePath);
        return new JsonFile(relativePath, content);
    }
}
```

**文件 2**：`addon-bridge-core/.../loader/AddonLoader.java`
```java
package net.easecation.bridge.core.loader;

public class AddonLoader {
    private final JSRuntime jsRuntime;
    private final ObjectMapper objectMapper;

    public AddonLoader(JSRuntime jsRuntime) {
        this.jsRuntime = jsRuntime;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 加载 Addon 目录
     */
    public ParsedAddon loadAddon(Path addonPath) throws IOException {
        // TODO: 扫描所有 JSON 文件
        List<JsonFile> files = scanFiles(addonPath);

        // TODO: 序列化为 JSON
        String filesJson = objectMapper.writeValueAsString(files);

        // TODO: 调用 JS 引擎解析
        String resultJson = jsRuntime.parseAddon(filesJson);

        // TODO: 反序列化为 Java DTO
        return objectMapper.readValue(resultJson, ParsedAddon.class);
    }

    private List<JsonFile> scanFiles(Path basePath) throws IOException {
        // TODO: 递归扫描 .json 文件
        return Files.walk(basePath)
            .filter(p -> p.toString().endsWith(".json"))
            .map(p -> JsonFile.of(basePath, p))
            .toList();
    }
}
```

**文件 3**：`addon-bridge-core/.../dto/ParsedAddon.java`
```java
package net.easecation.bridge.core.dto;

/**
 * 解析后的 Addon（标准化格式）
 */
public record ParsedAddon(
    List<StandardBlock> blocks,
    List<StandardItem> items,
    List<StandardEntity> entities,
    AddonMetadata metadata
) {
    public record AddonMetadata(
        String addonName,
        String description,
        List<String> warnings
    ) {}
}
```

**验收标准**：
- [ ] Java 能成功传递 JSON 字符串到 JS
- [ ] JS 能返回 JSON 字符串到 Java
- [ ] 数据往返延迟 < 5ms（基准测试）
- [ ] 异常处理完善（JS 错误能正确传递到 Java）

---

#### 任务 1.4：配置 TypeScript 编译环境 [1天]

**tsconfig.json**：
```json
{
  "compilerOptions": {
    "target": "ES2020",
    "module": "ESNext",
    "lib": ["ES2020"],
    "outDir": "./dist",
    "rootDir": "./src/main/typescript",
    "strict": true,
    "esModuleInterop": true,
    "skipLibCheck": true,
    "moduleResolution": "node",
    "resolveJsonModule": true,
    "declaration": true,
    "declarationMap": true,
    "sourceMap": true
  },
  "include": ["src/main/typescript/**/*"],
  "exclude": ["node_modules", "dist", "src/test"]
}
```

**webpack.config.js**（打包为单文件）：
```javascript
module.exports = {
  entry: './src/main/typescript/index.ts',
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'src/main/resources'),
    library: {
      type: 'var',
      name: 'AddonBridgeRuntime'
    }
  },
  module: {
    rules: [
      {
        test: /\.ts$/,
        use: 'ts-loader',
        exclude: /node_modules/
      }
    ]
  },
  resolve: {
    extensions: ['.ts', '.js']
  },
  mode: 'production',
  optimization: {
    minimize: false  // 保持可读性，便于调试
  }
};
```

**验收标准**：
- [ ] TypeScript 编译成功
- [ ] Webpack 打包生成 bundle.js
- [ ] 生成的 JS 能在 GraalVM 中运行
- [ ] 包含 Source Map 便于调试

---

#### 任务 1.5：集成 schema-types-ts 生成的类型 [2天]

**方案 1：软链接（开发阶段）**
```bash
# 在 js-runtime/src/main/typescript/ 下
ln -s ../../../../schema-types-ts/types ./types
```

**方案 2：复制文件（生产阶段）**
```gradle
// build.gradle.kts
tasks.register<Copy>("copySchemaTypes") {
    from("../schema-types-ts/types")
    into("src/main/typescript/types")
}

tasks.named("compileTypeScript") {
    dependsOn("copySchemaTypes")
}
```

**使用示例**：
```typescript
// js-runtime/src/main/typescript/parser/BlockParser.ts
import type { Blocks as Blocks_v1_21_60 } from '../types/behavior/blocks/v1_21_60';
import type { Blocks as Blocks_v1_20_10 } from '../types/behavior/blocks/v1_20_10';

export class BlockParser {
  parseBlock_v1_21_60(json: string): Blocks_v1_21_60 {
    // TODO: 解析逻辑，类型安全
    return JSON.parse(json);
  }
}
```

**验收标准**：
- [ ] TypeScript 能识别导入的类型
- [ ] IDE 提供完整的类型提示
- [ ] 编译时类型检查生效
- [ ] 类型定义与 JSON Schema 一致

---

#### 任务 1.6：编写基础单元测试 [1天]

**测试文件**：`js-runtime/src/test/typescript/__tests__/JSRuntime.test.ts`
```typescript
import { JSRuntime } from '../../../main/typescript/runtime/JSRuntime';

describe('JSRuntime', () => {
  let runtime: JSRuntime;

  beforeAll(() => {
    runtime = new JSRuntime();
  });

  afterAll(() => {
    runtime.close();
  });

  test('should parse empty addon', () => {
    const input = JSON.stringify([]);
    const result = runtime.parseAddon(input);
    const parsed = JSON.parse(result);

    expect(parsed.blocks).toEqual([]);
    expect(parsed.items).toEqual([]);
  });

  test('should handle malformed JSON', () => {
    const input = 'invalid json';
    expect(() => runtime.parseAddon(input)).toThrow();
  });
});
```

**验收标准**：
- [ ] 单元测试覆盖率 > 70%
- [ ] 所有测试通过
- [ ] 测试运行时间 < 5秒

---

### 4.3 Phase 2: Block 模块试点（详细任务）

#### 任务 2.1：设计 StandardBlock DTO [1天]

**文件**：`addon-bridge-core/.../dto/StandardBlock.java`

```java
package net.easecation.bridge.core.dto;

/**
 * 标准化的 Block 定义（最新版本格式）
 * 所有旧版本 Block 都会升级到此格式
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StandardBlock(
    // === 核心字段 ===
    @JsonProperty("identifier") String identifier,
    @JsonProperty("display_name") @Nullable String displayName,

    // === 物理属性（扁平化） ===
    @JsonProperty("hardness") @Nullable Float hardness,
    @JsonProperty("friction") @Nullable Float friction,
    @JsonProperty("light_emission") @Nullable Float lightEmission,     // 统一为 0.0-1.0
    @JsonProperty("light_dampening") @Nullable Integer lightDampening,
    @JsonProperty("explosion_resistance") @Nullable Float explosionResistance,

    // === 行为属性 ===
    @JsonProperty("is_waterloggable") @Nullable Boolean isWaterloggable,
    @JsonProperty("is_solid") @Nullable Boolean isSolid,
    @JsonProperty("can_contain_liquid") @Nullable Boolean canContainLiquid,

    // === 视觉属性 ===
    @JsonProperty("geometry") @Nullable String geometry,
    @JsonProperty("material_instances") @Nullable Map<String, Object> materialInstances,
    @JsonProperty("collision_box") @Nullable BoundingBox collisionBox,
    @JsonProperty("selection_box") @Nullable BoundingBox selectionBox,

    // === 高级组件（保留原始结构） ===
    @JsonProperty("components") @Nullable Map<String, Object> components,

    // === 元数据 ===
    @JsonProperty("metadata") BlockMetadata metadata
) {
    public record BoundingBox(
        @JsonProperty("origin") List<Float> origin,  // [x, y, z]
        @JsonProperty("size") List<Float> size       // [width, height, depth]
    ) {}

    public record BlockMetadata(
        @JsonProperty("source_version") String sourceVersion,       // "1.19.40"
        @JsonProperty("upgrade_path") List<String> upgradePath,     // ["1.19.40", "1.20.10", "1.21.60"]
        @JsonProperty("warnings") List<String> warnings,            // 转换警告
        @JsonProperty("source_file") String sourceFile              // "blocks/custom_block.json"
    ) {}
}
```

**设计原则**：
1. **扁平化**：常用字段直接提升到顶层
2. **类型统一**：lightEmission 统一为 float (0.0-1.0)
3. **保留灵活性**：components 字段保留未扁平化的组件
4. **元数据完整**：便于调试和追溯

**验收标准**：
- [ ] DTO 设计经过 Code Review
- [ ] 所有常用字段都已扁平化
- [ ] Jackson 注解完整
- [ ] 包含详细的 Javadoc

---

#### 任务 2.2：实现 Block 解析器（单版本）[2天]

**文件**：`js-runtime/src/main/typescript/parser/BlockParser.ts`

```typescript
import type { Blocks as Blocks_v1_21_60 } from '../types/behavior/blocks/v1_21_60';
import Ajv from 'ajv';
import addFormats from 'ajv-formats';

export class BlockParser {
  private ajv: Ajv;

  constructor() {
    this.ajv = new Ajv({ strict: false });
    addFormats(this.ajv);
    // TODO: 加载 Block v1.21.60 的 JSON Schema
  }

  /**
   * 解析 Block JSON（v1.21.60 版本）
   */
  parseBlock_v1_21_60(json: string, filePath: string): Blocks_v1_21_60 {
    // TODO: 解析 JSON
    const parsed = JSON.parse(json);

    // TODO: 验证 Schema（可选，用于调试）
    // const valid = this.ajv.validate(blockSchema, parsed);
    // if (!valid) {
    //   throw new Error(`Invalid block: ${this.ajv.errorsText()}`);
    // }

    return parsed as Blocks_v1_21_60;
  }

  /**
   * 提取 format_version
   */
  extractVersion(json: string): string {
    const parsed = JSON.parse(json);
    return parsed.format_version || '1.16.100';  // 默认版本
  }
}
```

**验收标准**：
- [ ] 能解析有效的 Block JSON
- [ ] 能识别 format_version
- [ ] Schema 验证可选启用
- [ ] 错误信息清晰（包含文件路径）

---

#### 任务 2.3：实现 Block 版本升级器（3-4 个版本）[4天]

**关键版本升级路径**（基于调研）：
```
1.19.0 → 1.19.40 → 1.19.50 → 1.20.10 → 1.20.41 → 1.20.81 → 1.21.50 → 1.21.60
```

**优先实现的升级器**：
1. `1.19.40 → 1.20.10`（光照字段变更）
2. `1.20.10 → 1.20.41`
3. `1.20.41 → 1.21.60`

**文件**：`js-runtime/src/main/typescript/upgrader/BlockUpgrader.ts`

```typescript
import type { Blocks as Blocks_v1_19_40 } from '../types/behavior/blocks/v1_19_40';
import type { Blocks as Blocks_v1_20_10 } from '../types/behavior/blocks/v1_20_10';
import type { Blocks as Blocks_v1_21_60 } from '../types/behavior/blocks/v1_21_60';

export class BlockUpgrader {
  /**
   * 支持的版本序列（按时间顺序）
   */
  private static readonly VERSION_SEQUENCE = [
    '1.19.0', '1.19.40', '1.19.50', '1.20.10',
    '1.20.41', '1.20.81', '1.21.50', '1.21.60'
  ];

  private static readonly LATEST_VERSION = '1.21.60';

  /**
   * 升级器映射表（版本 → 升级函数）
   */
  private static upgraders = new Map<string, (data: any, warnings: string[]) => any>([
    ['1.19.0', (data, warnings) => this.upgrade_v1_19_0_to_v1_19_40(data, warnings)],
    ['1.19.40', (data, warnings) => this.upgrade_v1_19_40_to_v1_19_50(data, warnings)],
    ['1.19.50', (data, warnings) => this.upgrade_v1_19_50_to_v1_20_10(data, warnings)],
    ['1.20.10', (data, warnings) => this.upgrade_v1_20_10_to_v1_20_41(data, warnings)],
    ['1.20.41', (data, warnings) => this.upgrade_v1_20_41_to_v1_20_81(data, warnings)],
    ['1.20.81', (data, warnings) => this.upgrade_v1_20_81_to_v1_21_50(data, warnings)],
    ['1.21.50', (data, warnings) => this.upgrade_v1_21_50_to_v1_21_60(data, warnings)],
  ]);

  /**
   * 线性升级到最新版本
   * 注意：始终按版本序列线性升级，不寻找最短路径（开服时运行一次，性能要求不高）
   */
  public static upgradeToLatest(
    data: any,
    fromVersion: string
  ): { data: Blocks_v1_21_60, upgradePath: string[], warnings: string[] } {
    const warnings: string[] = [];
    const upgradePath: string[] = [fromVersion];

    // TODO: 找到起始版本的索引
    const startIndex = this.VERSION_SEQUENCE.indexOf(fromVersion);
    if (startIndex === -1) {
      throw new Error(`Unsupported version: ${fromVersion}`);
    }

    // TODO: 线性升级到最新版本
    let current = data;
    for (let i = startIndex; i < this.VERSION_SEQUENCE.length - 1; i++) {
      const currentVersion = this.VERSION_SEQUENCE[i];
      const nextVersion = this.VERSION_SEQUENCE[i + 1];

      const upgrader = this.upgraders.get(currentVersion);
      if (!upgrader) {
        // 如果某个版本没有升级器，说明该版本到下一版本无变化，直接跳过
        warnings.push(`[Upgrade] ${currentVersion} → ${nextVersion}: No changes`);
        upgradePath.push(nextVersion);
        continue;
      }

      // 执行升级
      current = upgrader(current, warnings);
      upgradePath.push(nextVersion);
    }

    return { data: current, upgradePath, warnings };
  }

  /**
   * 1.19.40 → 1.19.50 升级器
   * 主要变更：无重大变更（保持兼容）
   */
  private static upgrade_v1_19_40_to_v1_19_50(
    data: any,
    warnings: string[]
  ): any {
    const result: any = { ...data };
    // TODO: 处理字段变更（如果有）
    result.format_version = '1.19.50';
    return result;
  }

  /**
   * 1.19.50 → 1.20.10 升级器
   * 主要变更：
   * - minecraft:light_emission: int (0-15) → minecraft:block_light_emission: float (0.0-1.0)
   */
  private static upgrade_v1_19_50_to_v1_20_10(
    data: any,
    warnings: string[]
  ): any {
    const result: any = { ...data };

    // TODO: 遍历所有 block
    for (const [blockId, blockDef] of Object.entries(result)) {
      if (typeof blockDef !== 'object') continue;

      const minecraftBlock = (blockDef as any)['minecraft:block'];
      if (!minecraftBlock) continue;

      const components = minecraftBlock.components || {};

      // TODO: 转换光照字段
      if (components['minecraft:light_emission'] !== undefined) {
        const oldValue = components['minecraft:light_emission'];
        const newValue = oldValue / 15.0;  // 转换公式

        components['minecraft:block_light_emission'] = newValue;
        delete components['minecraft:light_emission'];

        warnings.push(
          `[${blockId}] Converted light_emission: ${oldValue} → ${newValue}`
        );
      }

      // TODO: 处理其他字段变更...
    }

    result.format_version = '1.20.10';
    return result;
  }

  /**
   * TODO: 其他升级器实现...
   *
   * 模板：
   * private static upgrade_vX_Y_Z_to_vA_B_C(data: any, warnings: string[]): any {
   *   const result: any = { ...data };
   *   // TODO: 处理字段变更
   *   result.format_version = 'A.B.C';
   *   return result;
   * }
   */
  private static upgrade_v1_19_0_to_v1_19_40(data: any, warnings: string[]): any {
    // TODO: 实现
    return { ...data, format_version: '1.19.40' };
  }

  private static upgrade_v1_20_10_to_v1_20_41(data: any, warnings: string[]): any {
    // TODO: 实现
    return { ...data, format_version: '1.20.41' };
  }

  private static upgrade_v1_20_41_to_v1_20_81(data: any, warnings: string[]): any {
    // TODO: 实现
    return { ...data, format_version: '1.20.81' };
  }

  private static upgrade_v1_20_81_to_v1_21_50(data: any, warnings: string[]): any {
    // TODO: 实现
    return { ...data, format_version: '1.21.50' };
  }

  private static upgrade_v1_21_50_to_v1_21_60(data: any, warnings: string[]): any {
    // TODO: 实现
    return { ...data, format_version: '1.21.60' };
  }
}
```

**验收标准**：
- [ ] 实现至少 3 个版本升级器（优先实现有实际字段变更的版本）
- [ ] 线性升级逻辑正确（按 VERSION_SEQUENCE 顺序执行）
- [ ] 升级准确率 > 95%（与手动升级对比）
- [ ] 警告信息详细（包含升级路径和字段转换信息）
- [ ] 对于无变更的版本，能正确跳过并记录警告

---

#### 任务 2.4：实现 Block 标准化转换器 [2天]

**文件**：`js-runtime/src/main/typescript/converter/BlockConverter.ts`

```typescript
import type { Blocks as Blocks_v1_21_60 } from '../types/behavior/blocks/v1_21_60';
import type { StandardBlock } from '../types/standard';

export class BlockConverter {
  /**
   * 转换为标准化格式
   */
  public static convertToStandard(
    blocks: Blocks_v1_21_60,
    metadata: {
      sourceVersion: string;
      upgradePath: string[];
      warnings: string[];
      sourceFile: string;
    }
  ): StandardBlock[] {
    const result: StandardBlock[] = [];

    // TODO: 遍历所有 block
    for (const [blockId, blockDef] of Object.entries(blocks)) {
      if (typeof blockDef !== 'object') continue;

      const minecraftBlock = (blockDef as any)['minecraft:block'];
      if (!minecraftBlock) continue;

      const description = minecraftBlock.description || {};
      const components = minecraftBlock.components || {};

      // TODO: 提取并扁平化字段
      const standard: StandardBlock = {
        identifier: description.identifier || blockId,
        displayName: components['minecraft:display_name'],

        // 物理属性
        hardness: this.extractFloat(components['minecraft:destructible_by_mining']?.seconds_to_destroy),
        friction: this.extractFloat(components['minecraft:friction']),
        lightEmission: this.extractFloat(components['minecraft:block_light_emission']),
        lightDampening: this.extractInt(components['minecraft:light_dampening']),

        // 行为属性
        isWaterloggable: components['minecraft:waterloggable'] !== undefined,

        // 视觉属性
        geometry: this.extractGeometry(components['minecraft:geometry']),

        // 保留原始组件
        components: components,

        // 元数据
        metadata: {
          sourceVersion: metadata.sourceVersion,
          upgradePath: metadata.upgradePath,
          warnings: metadata.warnings,
          sourceFile: metadata.sourceFile
        }
      };

      result.push(standard);
    }

    return result;
  }

  /**
   * 辅助方法：提取浮点数
   */
  private static extractFloat(value: any): number | null {
    if (typeof value === 'number') return value;
    if (typeof value === 'object' && value !== null && 'value' in value) {
      return value.value;
    }
    return null;
  }

  // TODO: 其他辅助方法...
}
```

**验收标准**：
- [ ] 所有常用字段正确提取
- [ ] 多态类型正确归一化
- [ ] 元数据完整
- [ ] 单元测试覆盖率 > 80%

---

#### 任务 2.5：集成测试与调试 [1天]

**测试用例**：
```typescript
describe('Block E2E Test', () => {
  test('should parse and upgrade v1.19.40 block', () => {
    const input = `{
      "format_version": "1.19.40",
      "minecraft:block": {
        "description": {
          "identifier": "custom:glowing_block"
        },
        "components": {
          "minecraft:light_emission": 15
        }
      }
    }`;

    // 解析
    const parsed = BlockParser.parseBlock(input, 'v1.19.40');

    // 升级
    const { data: upgraded, warnings } = BlockUpgrader.upgradeToLatest(parsed, '1.19.40');

    // 验证
    expect(upgraded['minecraft:block'].components['minecraft:block_light_emission']).toBe(1.0);
    expect(warnings).toContain('Converted light_emission: 15 → 1.0');

    // 转换
    const standard = BlockConverter.convertToStandard(upgraded, {
      sourceVersion: '1.19.40',
      upgradePath: ['1.19.40', '1.20.10', '1.21.60'],
      warnings,
      sourceFile: 'blocks/glowing_block.json'
    });

    expect(standard[0].identifier).toBe('custom:glowing_block');
    expect(standard[0].lightEmission).toBe(1.0);
  });
});
```

**验收标准**：
- [ ] E2E 测试通过
- [ ] 能解析真实 JSON 文件（至少 10 个）
- [ ] 覆盖 3+ 个不同版本
- [ ] 性能测试：单文件 < 10ms

---

### 4.4 Phase 3-5 任务概览

#### Phase 3: Item/Entity 模块扩展 [10天]

复用 Block 模块的经验和模式：

| 任务 | 说明 | 预计时间 |
|------|------|---------|
| 3.1 设计 StandardItem/Entity DTO | 参考 StandardBlock | 1天 |
| 3.2 实现 Item 解析器 | 复用 BlockParser 模式 | 1天 |
| 3.3 实现 Item 升级器 | 3-4 个版本升级器 | 2天 |
| 3.4 实现 Entity 解析器 | 类似 Item | 1天 |
| 3.5 实现 Entity 升级器 | Entity 更复杂 | 3天 |
| 3.6 统一标准化转换 | 抽象公共逻辑 | 1天 |
| 3.7 集成测试 | 跨模块测试 | 1天 |

#### Phase 4: 自动化工具链 [5天]

| 任务 | 说明 | 预计时间 |
|------|------|---------|
| 4.1 Schema Diff 工具 | JSON Schema 对比引擎 | 2天 |
| 4.2 升级器骨架生成 | 基于 Diff 结果生成模板 | 2天 |
| 4.3 单元测试完善 | 覆盖率 > 80% | 1天 |

#### Phase 5: 性能优化与文档 [5天]

| 任务 | 说明 | 预计时间 |
|------|------|---------|
| 5.1 JS 引擎预热 | 启动时预编译 | 1天 |
| 5.2 缓存机制 | 缓存已解析的 Schema | 1天 |
| 5.3 性能基准测试 | 对比旧方案 | 1天 |
| 5.4 开发者指南 | 详细文档和示例 | 2天 |

---

### 4.5 关键里程碑验收标准

| 里程碑 | 时间节点 | 验收标准 | 负责人 |
|--------|---------|---------|--------|
| **M1: Phase 1 完成** | 第 2 周末 | - [x] GraalVM JS 成功集成<br>- [x] Java ↔ JS 数据往返正常（延迟 < 5ms）<br>- [x] schema-types-ts 类型可用<br>- [x] 单元测试覆盖率 > 70% | Java + TS 开发者 |
| **M2: Phase 2 完成** | 第 4 周末 | - [x] StandardBlock DTO 设计完成<br>- [x] 能解析 10+ 个真实 Block JSON<br>- [x] 版本升级准确率 > 95%<br>- [x] 单文件解析 < 10ms | TS 开发者 |
| **M3: Phase 3 完成** | 第 6 周末 | - [x] StandardItem/Entity DTO 完成<br>- [x] Item/Entity 升级器实现<br>- [x] 跨模块集成测试通过 | TS 开发者 |
| **M4: Phase 4 完成** | 第 7 周末 | - [x] Schema Diff 工具可用<br>- [x] 升级器骨架自动生成<br>- [x] 测试覆盖率 > 80% | 自动化工程师 |
| **M5: 项目完成** | 第 8 周末 | - [x] 性能优化完成<br>- [x] 开发者指南完成<br>- [x] 发布 v0.2.0 | 架构师 |

---

### 4.6 风险与应对

| 风险类型 | 具体风险 | 概率 | 影响 | 应对措施 |
|---------|---------|------|------|---------|
| **技术风险** | GraalVM 性能不达标 | 中 | 高 | - 预设 fallback 方案（保留旧解析器）<br>- 提前进行性能基准测试 |
| **技术风险** | TS 类型定义不完整 | 低 | 中 | - 使用 Ajv 运行时验证<br>- 手动修正关键类型 |
| **进度风险** | 升级器实现超时 | 中 | 中 | - 优先实现核心版本（1.19.40, 1.20.10, 1.21.60）<br>- 其他版本可后续补充 |
| **维护风险** | Schema 频繁变更 | 高 | 中 | - Schema Diff 工具自动化<br>- CI/CD 自动检测 |
| **团队风险** | TS 技能不足 | 中 | 中 | - 提前培训<br>- Pair Programming |

---

## 5. 自动化工具链改进

### 5.1 代码生成流程

#### 旧流程（当前）

```
JSON Schema → TypeScript Codegen → Java DTO → 手动编写 Upgrader
   (10s)           (30s)              (5min)        (人工)
```

**问题**：
- Java DTO 生成慢、文件多
- Upgrader 需要手动编写
- Schema 变更需要重新生成所有代码

#### 新流程（目标）

```
JSON Schema → TS Types → 自动生成升级器骨架 → 人工补充逻辑 → JS 运行时
   (5s)        (10s)           (20s)              (可选)       (即时)
```

**改进**：
- TS Types 轻量级，生成快
- 自动对比两个版本差异，生成升级器模板
- JS 运行时无需编译，修改即生效

### 5.2 Schema Diff 工具

**目标**：自动对比两个版本的 Schema，生成升级建议

```bash
npm run schema:diff -- --from 1.19.40 --to 1.20.10 --module block

# 输出示例
Differences between Block v1.19.40 and v1.20.10:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
🔴 REMOVED:
  - components["minecraft:light_emission"]: integer (0-15)

🟢 ADDED:
  - components["minecraft:block_light_emission"]: number (0.0-1.0)

🟡 MODIFIED:
  - components["minecraft:geometry"]:
      string → string | { identifier: string; bone_visibility?: ... }

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Suggested upgrader template generated at:
  codegen/output/upgraders/block_v1_19_40_to_v1_20_10.template.ts
```

### 5.3 升级器骨架自动生成

**生成模板**：

```typescript
// AUTO-GENERATED TEMPLATE - DO NOT EDIT DIRECTLY
// Fill in TODO sections manually

export function upgradeBlock_v1_19_40_to_v1_20_10(
  data: Block_v1_19_40
): Block_v1_20_10 {
  const components = { ...data.components };

  // TODO: Handle REMOVED field 'minecraft:light_emission'
  // Suggestion: Convert to 'minecraft:block_light_emission' (int → float / 15.0)
  if (components['minecraft:light_emission'] !== undefined) {
    // TODO: Implement conversion logic
  }

  // TODO: Handle MODIFIED field 'minecraft:geometry'
  // Old type: string
  // New type: string | { identifier: string; bone_visibility?: ... }
  if (components['minecraft:geometry'] !== undefined) {
    // TODO: Decide if string should wrap into object
  }

  return {
    format_version: '1.20.10',
    'minecraft:block': {
      description: data['minecraft:block'].description,
      components
    }
  };
}
```

---

## 6. 风险评估与缓解

### 6.1 技术风险

| 风险 | 概率 | 影响 | 缓解措施 |
|------|------|------|---------|
| **GraalVM 性能问题** | 🟡 中 | 🔴 高 | - 预热 JS 引擎<br>- 缓存编译结果<br>- 基准测试（目标：单文件 <10ms） |
| **Java ↔ JS 数据传递开销** | 🟡 中 | 🟠 中 | - 使用 JSON 字符串而非对象映射<br>- 批量传递减少往返次数 |
| **TS 类型定义不完整** | 🟢 低 | 🟠 中 | - 使用 Ajv 运行时验证<br>- 手动修正生成的 Types |
| **升级逻辑错误** | 🟠 中 | 🔴 高 | - 单元测试覆盖 80%+<br>- 使用真实 JSON 文件集成测试 |

### 6.2 维护风险

| 风险 | 缓解措施 |
|------|---------|
| **Schema 频繁变更** | - 自动化 Schema Diff 工具<br>- CI/CD 自动检测 Schema 更新 |
| **升级器逻辑复杂** | - 模块化设计，每个升级器独立<br>- 详细注释和文档 |
| **团队熟悉度低** | - 编写详细的开发指南<br>- Pair Programming 培训 |

### 6.3 兼容性风险

| 风险 | 缓解措施 |
|------|---------|
| **现有 Addon 无法加载** | - 保留旧解析器作为 fallback<br>- 逐步迁移，提供兼容模式 |
| **性能回退** | - 性能基准测试（对比旧方案）<br>- 优化热路径（缓存、预编译） |

---

## 7. 开发体验优化

### 7.1 Java 开发者视角

#### 变化对比

| 旧方案 | 新方案 | 改进 |
|--------|--------|------|
| 处理 `MaxStackSize` 的 sealed interface + 模式匹配 | 直接使用 `item.getMaxStackSize()` 返回 `int` | ✅ 代码简化 70%+ |
| 需要理解版本间差异，处理多个 DTO 版本 | 只使用 `StandardBlock/Item/Entity` | ✅ 学习成本降低 |
| 修改 Schema 需要重新生成和编译 Java 代码 | Schema 变更只影响 JS 层，Java 无感知 | ✅ 编译速度提升 10x+ |
| IDE 加载 10,690 个文件，性能下降 | 只加载 100+ 个核心文件 | ✅ IDE 流畅度提升 |

#### 代码示例对比

**旧方案**：
```java
// 需要处理多态类型
if (item.components().minecraft_maxStackSize() instanceof MaxStackSize.MaxStackSize_Variant0 v0) {
    int value = v0.value().intValue();
} else if (item.components().minecraft_maxStackSize() instanceof MaxStackSize.MaxStackSize_Variant1 v1) {
    int value = v1.value().intValue();
}
```

**新方案**：
```java
// 简化为单一类型
int maxStackSize = standardItem.getMaxStackSize();  // 已归一化
```

### 7.2 Schema 维护者视角

#### 工作流改进

```
旧流程：
  Schema 更新 → 重新运行 codegen → 重新编译 Java → 手动测试
  ⏱️ 5-10 分钟

新流程：
  Schema 更新 → 重新生成 TS Types → 修改 JS 升级器 → 热重载测试
  ⏱️ 30 秒
```

#### 调试体验

```typescript
// JS 侧提供详细的调试信息
export function parseBlock(json: string): StandardBlock {
  try {
    const parsed = JSON.parse(json);
    const version = parsed.format_version;

    console.log(`[Block] Parsing version: ${version}`);

    const upgraded = upgradeToLatest(parsed, version);
    console.log(`[Block] Upgraded to ${LATEST_VERSION}`);

    const standard = convertToStandard(upgraded);
    console.log(`[Block] Standardized: ${standard.identifier}`);

    return standard;
  } catch (error) {
    // 详细的错误信息，包含源文件位置
    throw new ParseError(`Failed to parse block: ${error.message}`, {
      sourceJson: json,
      stack: error.stack
    });
  }
}
```

### 7.3 新增调试工具

#### 7.3.1 Addon Inspector（Web UI）

**功能**：可视化展示 Addon 解析结果

```bash
gradle runAddonInspector  # 启动 Web UI（localhost:8080）

# 上传 behavior_pack 文件夹
# 自动解析并展示：
  - ✅ 解析成功的文件（绿色）
  - ⚠️  解析有警告的文件（黄色）
  - ❌ 解析失败的文件（红色）

# 点击文件查看详情：
  - 原始 JSON
  - 升级路径
  - 标准化输出
  - 警告信息
```

#### 7.3.2 Upgrade Path Visualizer

**功能**：可视化版本升级路径

```
Block: custom_block.json (1.19.40)
  │
  ├─ 1.19.40 → 1.20.10
  │    ├─ minecraft:light_emission (int 15) → minecraft:block_light_emission (float 1.0)
  │    └─ minecraft:geometry (string "geo") → (string "geo")  [无变化]
  │
  └─ 1.20.10 → 1.21.60
       └─ [无重大变更]

Final: StandardBlock
  ├─ identifier: "custom:block"
  ├─ lightEmission: 1.0
  └─ geometry: "geo"
```

---

## 8. 成功指标

### 8.1 量化目标

| 指标 | 当前值 | 目标值 | 测量方式 |
|------|--------|--------|---------|
| **Java 文件数量** | 10,690 个 | < 500 个 | 统计 `dto/` 目录 |
| **首次编译时间** | ~5 分钟 | < 30 秒 | Gradle 构建日志 |
| **Schema 变更响应时间** | 5-10 分钟 | < 1 分钟 | 从 Schema 更新到测试完成 |
| **单文件解析性能** | N/A（未测量） | < 10ms | 基准测试 |
| **版本升级测试覆盖率** | 0% | > 80% | JaCoCo 报告 |

### 8.2 里程碑验收

#### M1: 基础设施完成（第 2 周）

- [x] GraalVM JS 成功集成，能执行 TypeScript 编译后的代码
- [x] Java ↔ JS 数据传递正常，往返延迟 < 5ms
- [x] schema-types-ts 能生成 Block v1.21.60 的 TS Types

#### M2: Block 模块试点成功（第 4 周）

- [x] 能解析 10+ 个真实 Block JSON（覆盖 3+ 个版本）
- [x] 版本升级准确率 > 95%（与手动升级对比）
- [x] StandardBlock DTO 能成功注册到 PNX 测试服务器

#### M3: 完整迁移（第 6-8 周）

- [x] Block/Item/Entity 三大模块全部迁移
- [x] 单元测试覆盖率 > 80%
- [x] 性能基准测试通过（单文件 < 10ms）

---

## 9. 附录

### 9.1 参考资料

| 资源 | 链接 | 用途 |
|------|------|------|
| **GraalVM JS 文档** | https://www.graalvm.org/latest/reference-manual/js/ | 引擎集成 |
| **json-schema-to-typescript** | https://github.com/bcherny/json-schema-to-typescript | TS 类型生成 |
| **Ajv JSON Schema Validator** | https://ajv.js.org/ | Schema 验证 |
| **Jackson 文档** | https://github.com/FasterXML/jackson | Java JSON 解析 |

### 9.2 关键文件清单

| 文件 | 作用 | 优先级 |
|------|------|--------|
| `addon-bridge-core/.../JSRuntime.java` | JS 引擎封装 | 🔴 高 |
| `js-runtime/src/AddonParser.ts` | JS 主入口 | 🔴 高 |
| `js-runtime/src/VersionUpgrader.ts` | 版本升级器 | 🔴 高 |
| `addon-bridge-core/.../StandardBlock.java` | 标准化 DTO | 🔴 高 |
| `codegen/tools/schema-diff.ts` | Schema 对比工具 | 🟠 中 |
| `docs/developer-guide.md` | 开发者指南 | 🟡 低 |

### 9.3 团队分工建议

| 角色 | 职责 | 技能要求 |
|------|------|---------|
| **架构师** | 整体设计、技术选型、Code Review | Java + TypeScript + 架构设计 |
| **Java 开发者** | JSRuntime、StandardDTO、适配器 | Java 17 + GraalVM |
| **TypeScript 开发者** | 升级器、解析器、工具链 | TypeScript + JSON Schema |
| **测试工程师** | 单元测试、集成测试、性能测试 | Jest + JUnit + 测试工具 |

---

## 10. 总结

### 核心价值

1. **降低维护成本**：从 10,690 个 Java 文件降低到 < 500 个，减少 90%+ 代码量
2. **提升开发效率**：Schema 变更响应时间从 5-10 分钟降低到 < 1 分钟
3. **优化开发体验**：Java 开发者只需关注简化的 DTO 和业务逻辑，无需理解复杂的 Schema
4. **增强可扩展性**：利用 JS 生态工具，快速适配新版本 Minecraft

### 下一步行动

1. **立即行动**（本周）：
   - [ ] 评审本计划文档，确认技术方案
   - [ ] 创建 `js-runtime` 模块骨架
   - [ ] 添加 GraalVM 依赖

2. **短期目标**（2 周内）：
   - [ ] 完成 Phase 1（基础设施搭建）
   - [ ] 开始 Phase 2（Block 模块试点）

3. **长期目标**（6-8 周内）：
   - [ ] 完成所有模块迁移
   - [ ] 发布 v0.2.0 版本
   - [ ] 编写迁移指南和教程

---

**文档维护者**: Nukkit Addon Bridge 团队
**最后更新**: 2025-01-08
**反馈渠道**: GitHub Issues / 团队会议
