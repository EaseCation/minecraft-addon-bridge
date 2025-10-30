# JSON Schema to Java DTO Code Generator

从 Minecraft 基岩版 JSON Schema 自动生成类型安全的 Java DTO 类，支持 Jackson 序列化/反序列化。

## 功能特性

- **Schema 驱动**：完全基于 JSON Schema 定义生成代码，零硬编码
- **类型安全**：使用 Java 17 特性（record、sealed interface）
- **多版本支持**：独立生成不同版本的 DTO，支持 8 个历史版本 (1.19.0 - 1.21.60)
- **批量生成**：一键生成所有版本，自动切换 git commit
- **智能类型推断**：自动处理 oneOf/anyOf（多态）、allOf（合并）等复杂模式
- **内联嵌套**：自动检测并生成嵌套类型，避免文件泛滥
- **灵活的枚举处理**：enum 值使用 String 类型，支持未来扩展
- **Jackson 集成**：自动生成 `@JsonProperty`、`@Nullable` 等注解
- **Javadoc 生成**：将 Schema 的 description 转换为格式化的 Javadoc 注释

## 快速开始

### 前置要求

- Node.js >= 18.x
- JDK 21+（用于编译验证）
- Git（用于 submodule）

### 安装依赖

```bash
cd codegen
npm install
```

### 初始化 Schema

首次使用需要初始化 Git Submodule：

```bash
cd ..
git submodule update --init --recursive
```

### 生成代码

#### 单版本生成

```bash
# 生成最新版本（从 schema 自动检测）
npm run generate

# 指定版本号
npm run generate -- --mc-version 1.21.60

# 强制重新生成已存在的版本
npm run generate -- --mc-version 1.21.60 --force
```

#### 批量生成所有版本

```bash
# 生成所有版本（item + block 模块）
npm run generate:all

# 只生成 item 模块的所有版本
npm run generate:item

# 只生成 block 模块的所有版本
npm run generate:block

# 预览模式（不实际生成文件）
npm run generate:preview

# 强制重新生成所有版本
npm run generate:all -- --force
```

生成的 Java 代码位于：
```
addon-bridge-core/src/main/java/net/easecation/bridge/core/dto/
├── item/
│   ├── v1_19_0/
│   ├── v1_20_81/
│   └── v1_21_60/
├── block/
│   ├── v1_19_0/
│   └── v1_21_60/
└── entity/
    └── v1_21_60/
```

## 架构设计

### 设计理念

本代码生成器采用**多层架构**设计，将 Schema 解析、类型推断、代码生成三个关注点清晰分离，实现高内聚低耦合：

1. **声明式优于命令式**：完全基于 JSON Schema 定义生成代码，避免硬编码业务逻辑
2. **类型安全优于灵活性**：优先生成强类型结构（record），仅在必要时退化为 Map/String
3. **版本隔离**：每个版本的 DTO 完全独立，互不干扰，支持多版本共存
4. **渐进式扩展**：模块化设计，支持按需添加新的类型支持、模板或转换规则

### 架构图

```
┌─────────────────────────────────────────────────────────────┐
│                        CLI Layer                            │
│  ┌──────────────┐              ┌──────────────────────┐     │
│  │  index.ts    │              │  generate-all.ts     │     │
│  │ (单版本生成)  │              │  (批量多版本生成)     │     │
│  └──────┬───────┘              └──────────┬───────────┘     │
└─────────┼──────────────────────────────────┼─────────────────┘
          │                                  │
          ▼                                  ▼
┌─────────────────────────────────────────────────────────────┐
│                      Core Engine                            │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │
│  │SchemaLoader  │─▶│TypeRegistry  │◀─│DependencyAnalyzer│  │
│  │($ref 解析)   │  │(类型注册表)   │  │(拓扑排序)         │  │
│  └──────┬───────┘  └──────┬───────┘  └──────────────────┘  │
└─────────┼──────────────────┼───────────────────────────────┘
          │                  │
          ▼                  ▼
┌─────────────────────────────────────────────────────────────┐
│                      Parser Layer                           │
│  ┌──────────────┐              ┌──────────────────────┐     │
│  │SchemaParser  │◀────────────▶│  TypeResolver        │     │
│  │(结构解析)     │              │  (类型推断引擎)       │     │
│  └──────┬───────┘              └──────────────────────┘     │
└─────────┼─────────────────────────────────────────────────┘
          │
          ▼
┌─────────────────────────────────────────────────────────────┐
│                    Generator Layer                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │
│  │JavaGenerator │─▶│TemplateEngine│─▶│AnnotationBuilder │  │
│  │(代码生成)     │  │(Handlebars)  │  │(Jackson 注解)     │  │
│  └──────┬───────┘  └──────────────┘  └──────────────────┘  │
└─────────┼─────────────────────────────────────────────────┘
          │
          ▼
┌─────────────────────────────────────────────────────────────┐
│                      Output Layer                           │
│  ┌──────────────┐              ┌──────────────────────┐     │
│  │ FileWriter   │─────────────▶│  Gradle Compiler     │     │
│  │(.java 文件)   │              │  (编译验证)           │     │
│  └──────────────┘              └──────────────────────┘     │
└─────────────────────────────────────────────────────────────┘
```

### 核心概念

#### 1. Schema 解析与类型注册

**SchemaLoader** 负责递归解析 `$ref` 引用，构建完整的类型依赖图：

```typescript
// $ref 解析示例
"#/definitions/Operator" → definitions['Operator']
"./items.json#/definitions/Component" → 跨文件引用
```

**TypeRegistry** 维护全局类型注册表，避免重复生成同名类型：

```typescript
// 类型去重
registry.register('Operator', operatorType);  // 首次注册
registry.get('Operator');                     // 后续引用
```

#### 2. 类型推断策略

**TypeResolver** 是核心类型推断引擎，决定每个 Schema 节点生成何种 Java 类型：

| JSON Schema 模式 | 推断策略 | 生成结果 |
|-----------------|---------|---------|
| `properties` 存在 | 优先生成结构化对象 | `record` / 嵌套类 |
| `oneOf` / `anyOf` | 多态类型检测 | `sealed interface` |
| `allOf` | 合并所有 properties | 展平的 `record` |
| `enum` | 字符串包装 | `record XYZ(String value)` |
| 仅 `additionalProperties` | 动态字典 | `Map<String, T>` |
| 空 Schema `{}` | 空对象语义 | `EmptyObject` |

#### 3. 依赖拓扑排序

**DependencyAnalyzer** 分析类型依赖关系，确保生成顺序正确：

```
Item → Components → Damage → ...
  ↓
  依赖分析
  ↓
[Damage, Durability, ..., Components, Item]  # 拓扑排序后
```

#### 4. 模板驱动生成

使用 Handlebars 模板实现代码生成，支持：

- **Record.hbs**：标准 record 类型（字段 ≤ 20）
- **Class.hbs**：大型类（字段 > 20，使用 Builder 模式）
- **SealedInterface.hbs**：多态类型（oneOf / anyOf）

## 核心模块

### 1. SchemaLoader（Schema 加载器）

**职责**：
- 递归解析 JSON Schema 文件
- 解析 `$ref` 引用（本地 + 跨文件）
- 构建完整的 Schema 树

**关键方法**：
```typescript
loadSchema(filePath: string): JSONSchema7
resolveRef(ref: string, context: string): JSONSchema7
```

**特性**：
- 支持循环引用检测（防止无限递归）
- 缓存已加载的 Schema（性能优化）
- 自动处理相对路径和绝对路径

---

### 2. TypeRegistry（类型注册表）

**职责**：
- 全局类型注册和查询
- 类型名称冲突检测
- 跨模块类型去重

**关键方法**：
```typescript
register(name: string, type: JavaType): void
get(name: string): JavaType | undefined
has(name: string): boolean
```

**特性**：
- 线程安全（单线程环境下简化实现）
- 支持命名空间隔离（不同版本独立注册表）
- 自动类型合并（CommonTypeDetector 集成）

---

### 3. SchemaParser（Schema 解析器）

**职责**：
- 将 JSON Schema 转换为中间表示（IR）
- 识别类型种类（record / sealed interface / enum）
- 处理嵌套类型和内联决策

**关键方法**：
```typescript
parseSchema(schema: JSONSchema7, name: string): JavaType
parseRecord(schema: JSONSchema7, name: string): RecordType
parseSealedInterface(schema: JSONSchema7, name: string): SealedInterfaceType
```

**核心逻辑**：
```typescript
// 内联嵌套类型判断
if (schema.properties && !schema.$ref && !schema.patternProperties) {
  return this.parseInlineRecord(schema, suggestedName);
}

// 多态类型检测
if (schema.oneOf || schema.anyOf) {
  return this.parseSealedInterface(schema, name);
}
```

---

### 4. TypeResolver（类型推断引擎）

**职责**：
- JSON Schema 基本类型 → Java 类型映射
- 复杂类型推断（object / array / oneOf）
- 泛型类型构造（List<T> / Map<K, V>）

**关键方法**：
```typescript
resolveType(schema: JSONSchema7, context: Context): string
resolveArrayType(schema: JSONSchema7): string
resolveObjectType(schema: JSONSchema7): string
```

**类型优先级**：
```
1. $ref 引用 → 解析引用类型
2. enum → record XYZ(String value)
3. oneOf/anyOf → sealed interface
4. allOf → 合并 properties
5. properties → record
6. additionalProperties → Map<String, T>
7. 基本类型 → String / Integer / Boolean
```

---

### 5. DependencyAnalyzer（依赖分析器）

**职责**：
- 构建类型依赖图（DAG）
- 拓扑排序（确保依赖先生成）
- 循环依赖检测

**算法**：
```typescript
// Kahn 算法拓扑排序
topologicalSort(types: JavaType[]): JavaType[] {
  const graph = buildDependencyGraph(types);
  const sorted = [];
  const queue = findNodesWithZeroInDegree(graph);

  while (queue.length > 0) {
    const node = queue.shift();
    sorted.push(node);
    // 移除出边，更新入度...
  }

  return sorted;
}
```

---

### 6. JavaGenerator（Java 代码生成器）

**职责**：
- 调度模板引擎生成代码
- 注解构建（Jackson / Nullable）
- 包名和导入管理

**关键方法**：
```typescript
generate(type: JavaType, packageName: string): string
buildAnnotations(type: JavaType): Annotation[]
resolveImports(type: JavaType): string[]
```

**生成决策树**：
```
JavaType
  ├─ RecordType
  │    ├─ fields.length ≤ 20 → Record.hbs
  │    └─ fields.length > 20 → Class.hbs (Builder)
  ├─ SealedInterfaceType → SealedInterface.hbs
  └─ EnumType → 已弃用（现使用 String 包装）
```

---

### 7. TemplateEngine（模板引擎）

**职责**：
- Handlebars 模板编译和渲染
- 自定义 Helper 函数注册
- 模板缓存管理

**Helper 函数**：
```handlebars
{{toJavaType "string"}}          → String
{{toUpperSnakeCase "maxValue"}}  → MAX_VALUE
{{indent 2 "code block"}}        → 缩进代码
{{javadoc description}}          → 格式化 Javadoc
```

---

### 8. VersionMapping（版本配置管理）

**职责**：
- 加载 `version-mapping.json` 配置
- 提供版本到 commit ID 的映射查询
- 支持模块级版本管理

**关键方法**：
```typescript
loadVersionMapping(): VersionMapping
getModuleVersions(module: string): VersionEntry[]
findCommitByVersion(module: string, version: string): string
```

**配置格式**：
```json
{
  "modules": {
    "item": [
      {
        "format_version": "1.21.60",
        "commit": "2d7ba565356605ee83bd052b068c358cbf0277eb",
        "notes": "MC 1.21.60 (2024-12-24)"
      }
    ]
  }
}
```

## 生成流程详解

### 阶段 1：Schema 加载与解析

```typescript
// 1. 加载主 Schema 文件
const schema = SchemaLoader.loadSchema('blocks/blocks.json');

// 2. 递归解析 $ref
const resolvedSchema = SchemaLoader.resolveAllRefs(schema);

// 3. 检测版本号
const version = VersionDetector.detectVersion(schema);
```

### 阶段 2：类型注册与分析

```typescript
// 4. 遍历 definitions，注册类型
for (const [name, def] of Object.entries(schema.definitions)) {
  const javaType = SchemaParser.parseSchema(def, name);
  TypeRegistry.register(name, javaType);
}

// 5. 依赖分析与排序
const sortedTypes = DependencyAnalyzer.topologicalSort(
  TypeRegistry.getAllTypes()
);
```

### 阶段 3：代码生成与写入

```typescript
// 6. 按依赖顺序生成代码
for (const type of sortedTypes) {
  const packageName = JavaNaming.getJavaPackage(
    type.sourceFile,
    version
  );
  const code = JavaGenerator.generate(type, packageName);

  // 7. 写入文件
  FileWriter.writeJavaFile(
    `dto/${module}/v${version}/${type.name}.java`,
    code
  );
}

// 8. 编译验证
Gradle.compile('addon-bridge-core:compileJava');
```

### 决策点

#### 决策点 1：是否内联嵌套类型？

```typescript
// 内联条件：
// 1. 有 properties 定义
// 2. 无 $ref 引用
// 3. 无 patternProperties（避免复杂模式）
// 4. 未被其他类型引用（通过 DependencyAnalyzer 检测）

if (shouldInline(schema)) {
  return generateNestedRecord(schema);  // 内联
} else {
  return generateTopLevelRecord(schema); // 独立文件
}
```

#### 决策点 2：Record 还是 Class？

```typescript
// Record：字段 ≤ 20，简洁不可变
// Class：字段 > 20，使用 Builder 模式提高可读性

if (fields.length <= 20) {
  return renderTemplate('Record.hbs', data);
} else {
  return renderTemplate('Class.hbs', data);
}
```

#### 决策点 3：强类型还是 Map？

```typescript
// 优先级：properties > additionalProperties
// 只有纯 additionalProperties 时才生成 Map

if (schema.properties && Object.keys(schema.properties).length > 0) {
  return generateRecord(schema);  // 强类型
} else if (schema.additionalProperties) {
  return `Map<String, ${resolveValueType(schema.additionalProperties)}>`;
}
```

## 类型系统

### 基本类型映射

| JSON Schema | Java 类型 | 说明 |
|-------------|-----------|------|
| `type: "string"` | `String` | - |
| `type: "integer"` | `Integer` | - |
| `type: "number"` | `Double` | - |
| `type: "boolean"` | `Boolean` | - |
| `type: "array"` | `List<T>` | 根据 `items` 确定 `T` |

### 复杂类型映射

| JSON Schema 模式 | Java 类型 | 示例 |
|-----------------|-----------|------|
| `properties` 定义 | `record` 或嵌套类 | `record Item(String identifier, Integer maxStackSize)` |
| `enum` 值列表 | `record XYZ(String value)` | `record Operator(String value)` - 灵活扩展 |
| `oneOf` / `anyOf` | `sealed interface` + 子类型 | Jackson DEDUCTION 自动推断 |
| `allOf` | 合并所有 properties | 展平到单个 record |
| 纯 `additionalProperties` | `Map<String, T>` | 动态字典类型 |
| `properties` + `additionalProperties` | `record`（优先） | 忽略 additionalProperties，使用强类型 |
| 空 Schema `{}` | `EmptyObject` | 全局单例，显式空对象语义 |

### 多态类型（oneOf / anyOf）

使用 Jackson `@JsonTypeInfo(use = DEDUCTION)` 自动推断子类型：

```java
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
    @JsonSubTypes.Type(StringValue.class),
    @JsonSubTypes.Type(RangeValue.class)
})
public sealed interface Value {
    record StringValue(String value) implements Value {}
    record RangeValue(Integer min, Integer max) implements Value {}
}
```

### 嵌套类型策略

**内联嵌套**（生成内部 record）：
- 有明确的 `properties` 定义
- 无 `$ref` 引用
- 无 `patternProperties` 复杂模式
- 未被其他类型引用

**独立文件**：
- 被多个类型引用（通过 DependencyAnalyzer 检测）
- 包含 `patternProperties`
- Schema 中显式定义在 `definitions`

## CLI 参数

### 单版本生成（npm run generate）

```bash
# 生成最新版本（自动检测）
npm run generate

# 指定版本号
npm run generate -- --mc-version 1.21.60

# 指定输出目录
npm run generate -- --output-dir ../addon-bridge-core/src/main/java

# 指定 schema 目录
npm run generate -- --schema-dir ../schemas/minecraft-bedrock-json-schemas/behavior

# 调试模式（输出详细日志）
npm run generate -- --debug

# 干运行（不写入文件）
npm run generate -- --dry-run

# 清理已有文件后生成
npm run generate -- --clean

# 强制重新生成已存在的版本
npm run generate -- --mc-version 1.21.60 --force
```

### 批量生成（npm run generate:all）

```bash
# 生成所有版本（根据 version-mapping.json）
npm run generate:all

# 只生成指定模块
npm run generate:item    # 只生成 item 模块
npm run generate:block   # 只生成 block 模块

# 预览模式（显示计划但不生成）
npm run generate:preview

# 强制重新生成所有版本
npm run generate:all -- --force
npm run generate:item -- --force
```

## 版本管理

### 新的目录结构（v2）

生成器使用**模块优先**的目录结构，方便版本升级系统：

```
dto/
├── item/              # item 模块
│   ├── v1_19_0/      # Item, Components, AllowOffHand, etc.
│   ├── v1_19_40/
│   ├── v1_20_81/
│   └── v1_21_60/
├── block/             # block 模块
│   ├── v1_19_0/      # BlockDefinitions, Component, etc.
│   └── v1_21_60/
└── entity/            # entity 模块
    └── v1_21_60/     # Entity, Components, Events, etc.
```

**优势**：
- 类名不带版本后缀，通过包名区分：`net.easecation.bridge.core.dto.item.v1_21_60.Item`
- 同一模块的不同版本放在一起，便于对比和升级
- 支持模块级别的独立版本控制（item 和 block 可以有不同的版本号）

### 版本映射配置

所有支持的版本定义在 `version-mapping.json`：

```json
{
  "modules": {
    "item": [
      {
        "format_version": "1.19.0",
        "commit": "c8128d1e1267d25ddf429ad64a5261b4c77f2087",
        "notes": "MC 1.19.0 - 最早可追溯版本 (2022-08-05)"
      },
      {
        "format_version": "1.21.60",
        "commit": "2d7ba565356605ee83bd052b068c358cbf0277eb",
        "notes": "MC 1.21.60 - 当前最新 (2024-12-24)"
      }
    ],
    "block": [...]
  }
}
```

**说明**：
- `format_version`: schema 中定义的版本号
- `commit`: 对应的 git commit ID（schema 仓库）
- `notes`: 版本说明（可选）

### 批量生成工作流程

`generate-all.ts` 脚本会自动：
1. 读取 `version-mapping.json` 配置
2. 遍历每个版本
3. 切换 schema 仓库到对应的 commit
4. 调用 `generate` 生成该版本的 DTO
5. 恢复到原始分支

```bash
# 一键生成所有历史版本
npm run generate:all

# 示例输出：
# ▶ 1.19.0 (c8128d1e...)
#   📌 Checkout: c8128d1e
#   🔨 生成 item v1.19.0...
#   ✅ 完成
# ▶ 1.21.60 (2d7ba565...)
#   📌 Checkout: 2d7ba565
#   🔨 生成 item v1.21.60...
#   ✅ 完成
```

### 手动生成单个历史版本

```bash
# 1. 切换 Schema 到历史版本
cd schemas/minecraft-bedrock-json-schemas
git checkout c8128d1e1267d25ddf429ad64a5261b4c77f2087

# 2. 生成历史版本 DTO
cd ../../codegen
npm run generate -- --mc-version 1.19.0

# 3. 切换回最新版本
cd ../schemas/minecraft-bedrock-json-schemas
git checkout main
```

## 生成代码示例

输入 Schema（简化）：
```json
{
  "type": "object",
  "properties": {
    "format_version": {
      "type": "string",
      "description": "The format version"
    },
    "minecraft:block": {
      "type": "object",
      "properties": {
        "description": {
          "type": "object",
          "properties": {
            "identifier": { "type": "string" }
          }
        }
      }
    }
  }
}
```

生成的 Java 代码：
```java
package net.easecation.bridge.core.dto.block.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/** Block Behavior */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BlockDefinitions(
        /** The format version */
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:block") MinecraftBlock minecraftBlock
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MinecraftBlock(
        @JsonProperty("description") Description description
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            @JsonProperty("identifier") String identifier
        ) {}
    }
}
```

## 设计决策

### 1. Enum 使用 String 包装而非 Java Enum

**设计**：`enum` 值生成为 `record XYZ(String value)` 而非 Java enum

```java
// Operator.java
@JsonIgnoreProperties(ignoreUnknown = true)
public record Operator(String value) {}

// 使用方式
operator.value()  // 获取原始字符串值
```

**理由**：
- **前向兼容**：Minecraft 频繁添加新枚举值，String 包装无需重新生成代码
- **版本隔离**：避免不同版本 enum 不兼容导致反序列化失败
- **简化代码**：减少大量小型 enum 文件

---

### 2. Properties 优先于 AdditionalProperties

**设计**：当 Schema 同时包含 `properties` 和 `additionalProperties` 时，优先生成强类型结构

```typescript
// TypeResolver 优先级
if (schema.properties && Object.keys(schema.properties).length > 0) {
  return generateRecord(schema);  // 强类型
} else if (schema.additionalProperties) {
  return `Map<String, T>`;  // 动态类型
}
```

**理由**：
- **类型安全**：强类型提供编译时检查，减少运行时错误
- **IDE 支持**：自动补全和类型提示
- **文档价值**：record 字段即文档

---

### 3. 内联嵌套 vs 独立文件

**内联条件**（生成内部 record）：
```java
public record Item(
    String identifier,
    // 内联嵌套类型
    Components components
) {
    public record Components(
        Damage damage,
        Durability durability
    ) {}
}
```

**独立文件条件**：
- 被多个类型引用
- Schema 中在 `definitions` 显式定义
- 包含 `patternProperties` 复杂模式

**理由**：
- 减少文件数量（当前生成 901 个类已是大量）
- 保持相关类型就近（便于理解和维护）
- 避免全局命名空间污染

---

### 4. Sealed Interface 用于多态类型

**设计**：`oneOf` / `anyOf` 生成 sealed interface + Jackson DEDUCTION

```java
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({...})
public sealed interface Value permits StringValue, RangeValue {
    record StringValue(String value) implements Value {}
    record RangeValue(Integer min, Integer max) implements Value {}
}
```

**理由**：
- **类型安全**：sealed 限制子类型范围，编译器强制完整性检查
- **模式匹配**：Java 17+ 支持 switch 模式匹配
- **无侵入**：Jackson DEDUCTION 自动推断，无需 type 字段

---

### 5. 空对象使用 EmptyObject 单例

**设计**：空 Schema `{}` 生成为全局 `EmptyObject` 类型

```java
// 避免使用 Object（语义不明确）
public class EmptyObject {
    private EmptyObject() {}  // 防止实例化
}
```

**理由**：
- **显式语义**：明确表达"有意为空"vs"未定义"
- **类型区分**：与 `Object` 区分开（后者通常是类型推断失败）
- **JSON 兼容**：序列化为 `{}`

## 扩展指南

### 添加新的类型支持

#### 1. 添加新的 JSON Schema 类型映射

编辑 `TypeResolver.ts`：

```typescript
// src/parsers/TypeResolver.ts
resolveType(schema: JSONSchema7, context: Context): string {
  // 添加自定义类型判断
  if (schema.format === 'date-time') {
    return 'java.time.Instant';  // 自定义映射
  }

  // ... 现有逻辑
}
```

#### 2. 添加新的注解支持

编辑 `AnnotationBuilder.ts`：

```typescript
// src/generator/AnnotationBuilder.ts
buildFieldAnnotations(field: Field): Annotation[] {
  const annotations = [];

  // 添加自定义注解
  if (field.description?.includes('@deprecated')) {
    annotations.push({
      name: 'Deprecated',
      imports: []
    });
  }

  return annotations;
}
```

#### 3. 添加自定义模板

创建新模板文件 `templates/CustomType.hbs`：

```handlebars
package {{packageName}};

{{#each imports}}
import {{this}};
{{/each}}

/**
 * {{description}}
 */
public class {{className}} {
    // 自定义结构
}
```

注册模板：

```typescript
// src/generator/TemplateEngine.ts
this.templates.set('CustomType', this.compileTemplate('CustomType.hbs'));
```

---

### 添加新的 Helper 函数

编辑 `TemplateEngine.ts`：

```typescript
// src/generator/TemplateEngine.ts
private registerHelpers(): void {
  // 添加自定义 Helper
  Handlebars.registerHelper('formatDate', (date: string) => {
    return new Date(date).toISOString();
  });

  // 在模板中使用：{{formatDate publishedAt}}
}
```

---

### 自定义命名规则

编辑 `JavaNaming.ts`：

```typescript
// src/utils/JavaNaming.ts
export function toClassName(name: string): string {
  // 自定义前缀
  if (name.startsWith('minecraft:')) {
    return 'MC' + toPascalCase(name.replace('minecraft:', ''));
  }

  // ... 现有逻辑
}
```

---

### 添加新模块支持

1. **更新 `version-mapping.json`**：

```json
{
  "modules": {
    "item": [...],
    "block": [...],
    "entity": [  // 新模块
      {
        "format_version": "1.21.60",
        "commit": "abc123...",
        "notes": "Entity support"
      }
    ]
  }
}
```

2. **更新 `package.json` scripts**：

```json
{
  "scripts": {
    "generate:entity": "ts-node src/generate-all.ts --module entity"
  }
}
```

3. **Schema 目录映射**：

编辑 `JavaNaming.ts` 的 `extractModuleName()` 函数添加新模块名称映射。

---

### 调试技巧

#### 1. 启用详细日志

```bash
npm run generate -- --debug
```

查看：
- Schema 解析过程
- 类型推断决策
- 依赖关系图

#### 2. 干运行模式

```bash
npm run generate -- --dry-run
```

预览将生成的文件列表，不实际写入。

#### 3. 单独测试某个 Schema

```typescript
// 创建测试脚本 test-schema.ts
import { SchemaParser } from './src/parsers/SchemaParser';

const schema = {
  type: 'object',
  properties: {
    test: { type: 'string' }
  }
};

const parser = new SchemaParser();
const result = parser.parseSchema(schema, 'TestType');
console.log(JSON.stringify(result, null, 2));
```

运行：
```bash
ts-node test-schema.ts
```

## 开发

### 开发模式（文件监听）

```bash
npm run dev
```

### 运行测试

```bash
npm test
```

### 构建

```bash
npm run build
```

## 常见问题

### 使用相关

**Q: 如何更新到新版本的 Minecraft Schema？**

```bash
cd schemas/minecraft-bedrock-json-schemas
git pull origin main
cd ../../codegen
npm run generate  # 生成最新版本
```

**Q: 如何添加新版本到 version-mapping.json？**

1. 在 schema 仓库查找对应版本的 commit ID：
   ```bash
   cd schemas/minecraft-bedrock-json-schemas
   git log --grep="1.22" --oneline
   ```
2. 编辑 `version-mapping.json`，添加新条目：
   ```json
   {
     "format_version": "1.22.0",
     "commit": "abc123def456...",
     "notes": "MC 1.22.0 (2025-01-01)"
   }
   ```
3. 运行批量生成：
   ```bash
   npm run generate:all
   ```

**Q: 批量生成时如何跳过某些版本？**

在 `version-mapping.json` 中将 `commit` 设置为 `"待填写"`，该版本会被自动跳过。

**Q: 如何只重新生成某个模块的某个版本？**

```bash
# 切换到对应 commit
cd schemas/minecraft-bedrock-json-schemas
git checkout <commit-id>

# 生成指定版本
cd ../../codegen
npm run generate -- --mc-version 1.21.60 --force
```

---

### 架构相关

**Q: 为什么 enum 使用 String 包装而非 Java enum？**

采用 `record XYZ(String value)` 设计是为了：
- **前向兼容**：Minecraft 频繁添加新枚举值，String 包装无需重新生成
- **版本隔离**：避免不同版本 enum 类型冲突
- **灵活性**：支持运行时动态值

参见 [设计决策 #1](#1-enum-使用-string-包装而非-java-enum)

**Q: 为什么某些字段是 `Object` 类型？**

可能原因：
1. **数组类型不明确**：Schema 中 `items` 未定义，无法推断元素类型
2. **复杂多态**：`anyOf` 包含过多分支，无法安全映射为 sealed interface
3. **Schema 不完整**：定义缺失或使用了生成器不支持的高级特性

解决方案：检查 Schema 定义，或在 `TypeResolver.ts` 中添加自定义映射。

**Q: Record 和 Class 的生成条件是什么？**

- **Record**：字段数量 ≤ 20，生成不可变 record
- **Class**：字段数量 > 20，生成 class + Builder 模式（提高可读性）

参见 `JavaGenerator` 的生成决策树。

**Q: 如何自定义类型映射规则？**

编辑 `TypeResolver.ts`：

```typescript
resolveType(schema: JSONSchema7, context: Context): string {
  // 添加自定义规则
  if (schema.format === 'uuid') {
    return 'java.util.UUID';
  }

  // ... 现有逻辑
}
```

参见 [扩展指南 - 添加新的类型支持](#添加新的类型支持)

---

### 调试相关

**Q: 生成的代码编译失败怎么办？**

生成器会自动调用 Gradle 验证。常见问题：
- **类名冲突**：Schema 中多个定义生成同名类型 → 检查 `definitions` 命名
- **循环依赖**：两个类型相互引用 → 使用 `@JsonIdentityInfo` 或重构 Schema
- **Jackson 兼容性**：注解版本不匹配 → 检查 `pom.xml` 依赖版本

启用详细日志查看生成过程：
```bash
npm run generate -- --debug
```

**Q: 如何查看生成器的决策过程？**

使用 `--debug` 和 `--dry-run` 结合：

```bash
npm run generate -- --debug --dry-run
```

输出包括：
- Schema 解析树
- 类型推断决策
- 依赖关系图
- 将生成的文件列表（不实际写入）

**Q: 生成了多少代码？**

当前统计（2025-01）：
- **901 个 Java 类**
- **8 个版本** (1.19.0 - 1.21.60)
- **17 个模块** (item, block, entity, animation, etc.)
- **约 45,000 行** Java 代码

## 参考资料

- [JSON Schema 规范](https://json-schema.org/specification.html)
- [Jackson 注解文档](https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations)
- [Java 17 Sealed Classes](https://openjdk.org/jeps/409)
- [Minecraft Bedrock 文档](https://learn.microsoft.com/en-us/minecraft/creator/)

## 技术方案

完整的技术方案和架构设计见：[docs/json-schema-to-java-codegen-plan.md](../docs/json-schema-to-java-codegen-plan.md)

## License

MIT
