# JSON Schema 到 Java DTO 代码生成器技术方案

## 1. 项目概述

### 1.1 目标
将 Minecraft 基岩版 Add-on 的 JSON Schema 定义自动转换为支持 Jackson 的 Java DTO 类，用于 addon-bridge-core 模块解析基岩版附加包的 JSON 文件。

### 1.2 背景
- **输入源**: `schemas/minecraft-bedrock-json-schemas/behavior` 目录下的 22 个 JSON Schema 文件
- **输出目标**: `addon-bridge-core/src/main/java/net/easecation/bridge/core/dto/behavior` 目录
- **核心要求**:
  1. 使用 TypeScript 编写代码生成器
  2. 完全支持 Jackson 序列化/反序列化
  3. 利用 Java 17 的 sealed class 处理多态类型（oneOf/anyOf）
  4. 包名结构严格映射 schema 目录结构
  5. 保留 description 到 Javadoc
  6. 避免硬编码，使用通用逻辑覆盖所有场景
  7. 使用 ts-node 直接运行

### 1.3 Schema 文件清单
```
behavior/
├── animations/animations.json
├── animation_controller/animation_controller.json
├── biomes/biomes.json
├── blocks/blocks.json
├── cameras/cameras.json
├── dialogue/dialogue.json
├── entities/entities.json
├── features/features.json
├── feature_rules/feature_rules.json
├── functions/tick.json
├── item_catalog/crafting_item_catalog.json
├── items/items.json
├── lighting/atmospherics.json
├── lighting/global.json
├── loot_tables/loot_tables.json
├── recipes/recipes.json
├── spawn_rules/spawn_rules.json
├── trading/trading.json
└── worldgen/
    ├── jigsaw.json
    ├── processor_list.json
    ├── structure_set.json
    └── template_pool.json
```

### 1.4 版本管理需求

**关键约束**：
1. **一次性生成所有模块**：必须在单次运行中生成所有模块（blocks, items, entities 等），以便全局分析公共类型
2. **多版本并存**：支持多个版本的 DTO 同时存在，用于版本升级系统（参见 `version-upgrade-system-plan.md`）
3. **历史版本生成**：通过 git checkout 到旧版本的 schema，重新运行 codegen 生成历史版本的 DTO
4. **独立的版本 DTO**：每个版本的类型完全独立，不使用继承或类型别名
5. **Upgrader 手动编写**：版本升级器由开发者手动编写，codegen 不生成

---

## 2. 技术选型

### 2.1 核心依赖
| 库 | 版本 | 用途 |
|---|---|---|
| `@apidevtools/json-schema-ref-parser` | ^11.x | 解析和解引用 JSON Schema（处理 $ref） |
| `typescript` | ^5.x | 类型安全的代码生成器开发 |
| `ts-node` | ^10.x | 直接运行 TypeScript 代码 |
| `handlebars` | ^4.x | Java 代码模板引擎 |
| `commander` | ^12.x | CLI 参数解析 |
| `chalk` | ^5.x | 终端输出美化（可选） |

### 2.2 开发环境
- **Node.js**: >= 18.x
- **TypeScript**: 5.x（使用 ES2022+ 特性）
- **Java Target**: Java 17（使用 sealed class、record、switch 表达式等）

---

## 3. 架构设计

### 3.1 分层架构

```
codegen/
├── src/
│   ├── core/                    # 核心引擎
│   │   ├── SchemaLoader.ts      # Schema 加载和解引用
│   │   ├── TypeRegistry.ts      # 全局类型注册表
│   │   ├── DependencyAnalyzer.ts # 类型依赖分析和排序
│   │   └── CommonTypeDetector.ts # 公共类型检测和去重
│   ├── parsers/                 # 解析器层
│   │   ├── SchemaParser.ts      # 主解析器入口
│   │   ├── TypeResolver.ts      # 类型解析和映射
│   │   └── types.ts             # 解析结果的 TS 类型定义
│   ├── generator/               # 代码生成器层（单一通用生成器）
│   │   ├── JavaGenerator.ts     # 通用 Java 代码生成器
│   │   ├── TemplateEngine.ts    # Handlebars 模板引擎封装
│   │   └── AnnotationBuilder.ts # Jackson 注解构建器
│   ├── templates/               # Handlebars 模板
│   │   ├── Record.hbs           # Java record 模板
│   │   ├── SealedInterface.hbs  # sealed interface 模板
│   │   ├── Enum.hbs             # Java enum 模板
│   │   └── Deserializer.hbs     # 自定义反序列化器模板（可选）
│   ├── utils/                   # 工具函数
│   │   ├── JavaNaming.ts        # Java 命名转换（驼峰、PascalCase）
│   │   ├── JavadocConverter.ts  # Markdown/HTML 到 Javadoc 转换
│   │   └── FileWriter.ts        # 文件写入工具
│   └── index.ts                 # CLI 入口
├── tsconfig.json
└── package.json
```

**设计原则**：
- ✅ **Schema-Driven**：完全由 JSON Schema 的结构驱动代码生成
- ✅ **零硬编码**：没有针对特定模块（blocks/items/entities）的特殊逻辑
- ✅ **单一通用生成器**：JavaGenerator 处理所有类型的代码生成
- ✅ **模板驱动**：通过 Handlebars 模板控制输出格式

### 3.2 数据流

```
JSON Schema 文件
    ↓
SchemaLoader（解引用 $ref）
    ↓
SchemaParser（解析结构）
    ↓
TypeRegistry（注册类型定义）
    ↓
DependencyAnalyzer（拓扑排序）
    ↓
JavaGenerator（代码生成）
    ↓
FileWriter（写入 .java 文件）
```

---

## 4. 核心模块设计

### 4.1 SchemaLoader 模块

**职责**: 加载 JSON Schema 并解析所有 `$ref` 引用

```typescript
interface SchemaLoader {
  /**
   * 加载并解引用 Schema
   * @param schemaPath - Schema 文件路径
   * @returns 完全解引用的 Schema 对象
   */
  loadSchema(schemaPath: string): Promise<JSONSchema7>;

  /**
   * 批量加载目录下的所有 Schema
   */
  loadDirectory(dirPath: string): Promise<Map<string, JSONSchema7>>;
}
```

**实现要点**:
- 使用 `json-schema-ref-parser` 的 `dereference()` 方法
- 处理内部引用（`#/definitions/...`）和外部引用（相对路径）
- 缓存已加载的 Schema 避免重复解析

### 4.2 TypeRegistry 模块

**职责**: 全局类型注册表，管理所有生成的 Java 类型

```typescript
interface TypeInfo {
  schemaPath: string;           // 原始 schema 文件路径
  schemaPointer: string;        // JSON Pointer（如 "#/definitions/Block"）
  javaPackage: string;          // 生成的 Java 包名
  javaClassName: string;        // Java 类名
  javaType: JavaTypeKind;       // record | sealed | enum | class
  dependencies: Set<string>;    // 依赖的其他类型
  schema: JSONSchema7;          // 原始 Schema 定义
}

enum JavaTypeKind {
  RECORD = 'record',
  SEALED_INTERFACE = 'sealed_interface',
  SEALED_CLASS = 'sealed_class',
  ENUM = 'enum',
  REGULAR_CLASS = 'class',
}

class TypeRegistry {
  register(typeInfo: TypeInfo): void;
  resolve(fullyQualifiedName: string): TypeInfo | undefined;
  getAllTypes(): TypeInfo[];
  getTypesByPackage(packageName: string): TypeInfo[];
}
```

**实现要点**:
- 全局唯一的类型注册中心
- 支持按包名、类名快速查找
- 记录类型之间的依赖关系

### 4.3 DependencyAnalyzer 模块

**职责**: 分析类型依赖关系，确定代码生成顺序

```typescript
class DependencyAnalyzer {
  /**
   * 拓扑排序，返回无环的生成顺序
   * @throws Error 如果检测到循环依赖
   */
  analyze(types: TypeInfo[]): TypeInfo[];

  /**
   * 检测循环依赖
   */
  detectCycles(types: TypeInfo[]): string[][] | null;
}
```

**实现要点**:
- 使用 Kahn 算法或 DFS 进行拓扑排序
- 循环依赖处理策略：
  1. 检测到循环依赖时报错并输出依赖链
  2. 对于合理的循环（如互相引用的 sealed 接口），使用前向声明或分离接口定义

### 4.4 SchemaParser 模块

**职责**: 将 JSON Schema 解析为中间表示（IR）

```typescript
interface ParsedType {
  name: string;
  kind: JavaTypeKind;
  description?: string;
  properties?: ParsedProperty[];
  oneOfVariants?: ParsedType[];
  enumValues?: string[];
  annotations: JavaAnnotation[];
}

interface ParsedProperty {
  name: string;                 // JSON 字段名
  javaName: string;             // Java 字段名（驼峰）
  type: string;                 // Java 类型（含泛型）
  required: boolean;
  description?: string;
  defaultValue?: string;
  annotations: JavaAnnotation[];
}

class SchemaParser {
  parse(schema: JSONSchema7, context: ParserContext): ParsedType;
}
```

**核心解析规则**:

#### 4.4.1 基础类型映射

| JSON Schema | Java 类型 | 说明 |
|-------------|-----------|------|
| `type: "string"` | `String` | - |
| `type: "integer"` | `Integer` | 可选：根据范围使用 `Long` |
| `type: "number"` | `Double` | 可选：根据精度使用 `Float` |
| `type: "boolean"` | `Boolean` | - |
| `type: "array"` | `List<T>` | 根据 `items` 确定 `T` |
| `type: "object"` | 生成新类 | 递归处理 |
| `enum` | `enum` | Java 枚举类 |

#### 4.4.2 oneOf/anyOf 处理策略

**场景**: 表示"多种可能的类型之一"

**Java 映射**: sealed interface + 实现类

```json
{
  "oneOf": [
    { "type": "string" },
    { "type": "object", "properties": { "min": {...}, "max": {...} } }
  ]
}
```

↓ 生成为

```java
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface RollsValue {
    record StringValue(String value) implements RollsValue {}
    record RangeValue(Integer min, Integer max) implements RollsValue {}
}
```

**Jackson 注解**:
- `@JsonTypeInfo(use = Id.DEDUCTION)`: 自动推断类型（无需显式 type 字段）
- 或使用 `@JsonSubTypes` + 自定义反序列化器

#### 4.4.3 allOf 处理策略

**场景1**: 单纯的属性合并
```json
{
  "allOf": [
    { "properties": { "name": {...} } },
    { "properties": { "age": {...} } }
  ]
}
```
→ 合并为单个 record

**场景2**: 继承基类
```json
{
  "allOf": [
    { "$ref": "#/definitions/BaseComponent" },
    { "properties": { "customField": {...} } }
  ]
}
```
→ 如果基类是接口，使用 `implements`；如果是类，考虑组合模式

#### 4.4.4 $ref 处理

- **内部引用** (`#/definitions/Foo`): 转换为 Java 类型引用
- **跨文件引用**: 根据文件路径推断包名和类名
- **循环引用**: 标记并在依赖分析阶段处理

### 4.5 JavaGenerator 模块（通用生成器）

**职责**: 根据 ParsedType 生成 Java 代码（支持所有模块，无硬编码）

```typescript
class JavaGenerator {
  private templateEngine: TemplateEngine;
  private annotationBuilder: AnnotationBuilder;
  private javadocConverter: JavadocConverter;

  /**
   * 生成 Java 代码（通用方法）
   * @param parsedType - 解析后的类型信息
   * @returns 生成的 Java 代码字符串
   */
  generate(parsedType: ParsedType): string {
    switch (parsedType.kind) {
      case JavaTypeKind.RECORD:
        return this.generateRecord(parsedType);
      case JavaTypeKind.SEALED_INTERFACE:
        return this.generateSealedInterface(parsedType);
      case JavaTypeKind.ENUM:
        return this.generateEnum(parsedType);
      default:
        throw new Error(`Unsupported type kind: ${parsedType.kind}`);
    }
  }

  private generateRecord(type: ParsedType): string {
    const data = {
      package: type.javaPackage,
      className: type.javaClassName,
      javadoc: this.javadocConverter.convert(type.description),
      annotations: this.annotationBuilder.buildClassAnnotations(type),
      properties: type.properties?.map(prop => ({
        name: prop.javaName,
        type: prop.type,
        annotations: this.annotationBuilder.buildPropertyAnnotations(prop),
        javadoc: this.javadocConverter.convert(prop.description)
      }))
    };
    return this.templateEngine.render('Record.hbs', data);
  }

  private generateSealedInterface(type: ParsedType): string {
    const data = {
      package: type.javaPackage,
      interfaceName: type.javaClassName,
      javadoc: this.javadocConverter.convert(type.description),
      annotations: this.annotationBuilder.buildClassAnnotations(type),
      variants: type.oneOfVariants?.map(variant => ({
        className: variant.javaClassName,
        properties: variant.properties
      }))
    };
    return this.templateEngine.render('SealedInterface.hbs', data);
  }

  private generateEnum(type: ParsedType): string {
    const data = {
      package: type.javaPackage,
      enumName: type.javaClassName,
      javadoc: this.javadocConverter.convert(type.description),
      values: type.enumValues?.map(value => ({
        javaName: toUpperSnakeCase(value),
        jsonValue: value
      }))
    };
    return this.templateEngine.render('Enum.hbs', data);
  }
}
```

**关键点**：
- ✅ **无模块区分**：blocks、items、entities 使用相同的生成逻辑
- ✅ **Schema 驱动**：根据 Schema 的 `type`、`oneOf`、`enum` 等字段决定生成什么
- ✅ **模板渲染**：所有格式化逻辑在 Handlebars 模板中
- ✅ **可扩展**：新增 Schema 特性只需添加新的模板或扩展解析器

#### 4.5.1 Record 模板（Record.hbs）

**适用场景**: 简单的数据对象（`type: "object"`，无 oneOf/anyOf）

**模板输入数据**:
```typescript
{
  package: 'net.easecation.bridge.core.dto.v1_21_60.behavior.blocks',
  className: 'BlockDefinition',
  javadoc: '/**\n * Block Behavior\n * ...\n */',
  annotations: ['@JsonIgnoreProperties(ignoreUnknown = true)'],
  properties: [
    { name: 'formatVersion', type: 'String', annotations: ['@JsonProperty("format_version")'], javadoc: '...' },
    { name: 'minecraftBlock', type: 'MinecraftBlock', annotations: ['@JsonProperty("minecraft:block")'], javadoc: '...' }
  ]
}
```

**生成结果**:
```java
package {{package}};

{{javadoc}}
{{#each annotations}}
{{this}}
{{/each}}
public record {{className}}(
    {{#each properties}}
    {{javadoc}}
    {{#each annotations}}{{this}} {{/each}}{{type}} {{name}}{{#unless @last}},{{/unless}}
    {{/each}}
) {}
```

#### 4.5.2 Sealed Interface 模板（SealedInterface.hbs）

**适用场景**: oneOf/anyOf 多态类型

**模板输入数据**:
```typescript
{
  package: 'net.easecation.bridge.core.dto.v1_21_60.behavior.blocks',
  interfaceName: 'CollisionBox',
  javadoc: '/**\n * Collision Box\n * ...\n */',
  annotations: ['@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)'],
  variants: [
    { className: 'BooleanValue', properties: [{ name: 'value', type: 'boolean' }] },
    { className: 'ObjectValue', properties: [{ name: 'origin', type: 'List<Double>' }, ...] }
  ]
}
```

**生成结果**:
```java
package {{package}};

{{javadoc}}
{{#each annotations}}
{{this}}
{{/each}}
public sealed interface {{interfaceName}} {
    {{#each variants}}
    record {{className}}(
        {{#each properties}}
        {{#each annotations}}{{this}} {{/each}}{{type}} {{name}}{{#unless @last}},{{/unless}}
        {{/each}}
    ) implements {{../interfaceName}} {}
    {{/each}}
}
```

#### 4.5.3 Enum 模板（Enum.hbs）

**适用场景**: JSON Schema 中的 `enum` 字段

**模板输入数据**:
```typescript
{
  package: 'net.easecation.bridge.core.dto.v1_21_60.behavior.blocks',
  enumName: 'RenderMethod',
  javadoc: '/**\n * Render Method\n * ...\n */',
  values: [
    { javaName: 'OPAQUE', jsonValue: 'opaque' },
    { javaName: 'DOUBLE_SIDED', jsonValue: 'double_sided' },
    ...
  ]
}
```

**生成结果**:
```java
package {{package}};

{{javadoc}}
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum {{enumName}} {
    {{#each values}}
    @JsonProperty("{{jsonValue}}") {{javaName}}{{#unless @last}},{{/unless}}
    {{/each}}
}
```

---

## 5. 关键技术点

### 5.1 版本化目录结构

**设计决策**：使用顶层版本目录

```
addon-bridge-core/src/main/java/net/easecation/bridge/core/
├── dto/
│   ├── v1_16_100/               ← 历史版本
│   │   ├── behavior/
│   │   │   ├── blocks/
│   │   │   │   ├── BlockDefinition.java
│   │   │   │   ├── BlockComponents.java
│   │   │   │   └── component/...
│   │   │   ├── items/
│   │   │   ├── entities/
│   │   │   └── recipes/
│   │   └── common/              ← 该版本内的公共类型
│   │       ├── BlockIdentifier.java
│   │       ├── Molang.java
│   │       └── CollisionBox.java
│   ├── v1_19_20/               ← 历史版本
│   │   ├── behavior/...
│   │   └── common/              ← 该版本内的公共类型
│   │       └── ...
│   ├── v1_21_60/               ← 当前最新版本
│   │   ├── behavior/
│   │   │   ├── blocks/
│   │   │   ├── items/
│   │   │   ├── entities/
│   │   │   └── recipes/
│   │   └── common/              ← 该版本内的公共类型
│   │       ├── BlockIdentifier.java
│   │       ├── Molang.java
│   │       └── CollisionBox.java
└── upgrade/                    ← 版本升级器（手动编写）
    └── block/
        ├── BlockUpgrader_1_16_100_to_1_19_20.java
        └── BlockUpgrader_1_19_20_to_1_21_60.java
```

**包名映射规则**：

`schemas/minecraft-bedrock-json-schemas/behavior/<path>/<file>.json`
→ `net.easecation.bridge.core.dto.v<version>.behavior.<path>.<ClassName>`

**示例**：
```
behavior/blocks/blocks.json (version: 1.21.60)
→ net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.BlockDefinition

behavior/items/items.json (version: 1.21.60)
→ net.easecation.bridge.core.dto.v1_21_60.behavior.items.ItemDefinition

behavior/loot_tables/loot_tables.json (version: 1.19.20)
→ net.easecation.bridge.core.dto.v1_19_20.behavior.loot_tables.LootTable
```

**实现**：
```typescript
function getJavaPackage(
  schemaPath: string,
  version: string  // e.g., "1.21.60"
): string {
  const versionSegment = `v${version.replace(/\./g, '_')}`;  // "v1_21_60"

  const relativePath = path.relative(
    'schemas/minecraft-bedrock-json-schemas/behavior',
    path.dirname(schemaPath)
  );

  const pathSegments = relativePath.split(path.sep).filter(s => s);
  return `net.easecation.bridge.core.dto.${versionSegment}.behavior${
    pathSegments.length > 0 ? '.' + pathSegments.join('.') : ''
  }`;
}
```

**为什么选择顶层版本目录**：
1. ✅ 一次性生成所有模块时，版本号是全局统一的
2. ✅ 公共类型的识别需要跨模块扫描，版本内公共类型便于管理
3. ✅ 归档简单：新版本发布时只需添加新的版本目录（如 v1_22_0/）
4. ✅ 版本完全独立：删除旧版本时直接删除整个版本目录
5. ✅ 与 `version-upgrade-system-plan.md` 设计一致

### 5.2 命名转换规则

| 场景 | 输入 | 输出 | 规则 |
|------|------|------|------|
| 类名 | `loot_tables.json` | `LootTable` | PascalCase，移除文件扩展名 |
| 字段名 | `format_version` | `formatVersion` | camelCase |
| 枚举值 | `double_sided` | `DOUBLE_SIDED` | UPPER_SNAKE_CASE |
| 包名 | `loot_tables` | `loot_tables` | 保持 snake_case（Java 约定） |

**工具函数**:
```typescript
// utils/JavaNaming.ts
export function toPascalCase(str: string): string;
export function toCamelCase(str: string): string;
export function toUpperSnakeCase(str: string): string;
export function sanitizeIdentifier(str: string): string; // 移除非法字符
```

### 5.3 Jackson 注解策略

#### 5.3.1 多态类型反序列化

**方案 A**: 使用 `@JsonTypeInfo(use = DEDUCTION)`
- 优点: 无需在 JSON 中添加额外 type 字段
- 缺点: 需要 Jackson 2.12+，类型推断可能失败

**方案 B**: 自定义 Deserializer
```java
@JsonDeserialize(using = CollisionBoxDeserializer.class)
public sealed interface CollisionBox { ... }

public class CollisionBoxDeserializer extends StdDeserializer<CollisionBox> {
    @Override
    public CollisionBox deserialize(JsonParser p, DeserializationContext ctxt) {
        JsonNode node = p.readValueAsTree();
        if (node.isBoolean()) {
            return new CollisionBox.BooleanValue(node.asBoolean());
        } else if (node.isObject()) {
            return objectMapper.treeToValue(node, CollisionBox.ObjectValue.class);
        }
        throw new IllegalArgumentException("Unknown type");
    }
}
```

**推荐**: 优先使用方案 A，复杂场景生成方案 B

#### 5.3.2 可选字段处理

**策略 1**: 使用 `@Nullable` + null 值
```java
public record Block(
    @Nullable String description
) {}
```

**策略 2**: 使用 `Optional<T>`
```java
public record Block(
    Optional<String> description
) {}
```

**推荐**: 使用策略 1（`@Nullable`），因为 Jackson 对 Optional 的支持需要额外配置

### 5.4 description 到 Javadoc 转换

**输入** (JSON Schema):
```json
{
  "description": "The **format version** tells minecraft what type of data format can be expected.\nSupported versions: `1.20.80`, `1.21.0`",
  "title": "Format Version"
}
```

**输出** (Javadoc):
```java
/**
 * Format Version
 * <p>
 * The <b>format version</b> tells minecraft what type of data format can be expected.
 * Supported versions: {@code 1.20.80}, {@code 1.21.0}
 */
```

**转换规则**:
- `**text**` → `<b>text</b>`
- `` `code` `` → `{@code code}`
- `\n` → `<p>`
- 转义 `@` 和 `*/` 等特殊字符

### 5.5 循环依赖处理

**场景**: A 引用 B，B 引用 A

**解决方案**:
1. **检测**: 在 DependencyAnalyzer 中使用 DFS 检测环
2. **打破循环**:
   - 如果是接口引用，可以直接生成（接口前向声明不需要完整定义）
   - 如果是类引用，报错并要求手动处理（极少见）

### 5.6 公共类型识别和去重

**目标**：识别同一版本内跨模块相同的类型定义，提取到版本内的 `common/` 包避免重复

**注意**：公共类型仅在版本内共享，不跨版本共享。例如：
- `dto/v1_21_60/common/CollisionBox.java` - 1.21.60 版本的公共类型
- `dto/v1_19_20/common/CollisionBox.java` - 1.19.20 版本的公共类型（可能结构不同）

**算法**：

#### 第一阶段：类型指纹计算

为每个类型定义计算结构化指纹：

```typescript
interface TypeFingerprint {
  kind: 'record' | 'sealed' | 'enum';
  properties: Array<{
    name: string;
    type: string;
    required: boolean;
  }>;
  hash: string;  // SHA256(JSON.stringify(properties))
}

function computeFingerprint(type: ParsedType): TypeFingerprint {
  const normalized = {
    kind: type.kind,
    properties: type.properties?.map(p => ({
      name: p.name,
      type: normalizeType(p.type),
      required: p.required
    })).sort((a, b) => a.name.localeCompare(b.name))
  };

  return {
    ...normalized,
    hash: sha256(JSON.stringify(normalized))
  };
}
```

#### 第二阶段：公共类型检测

```typescript
class CommonTypeDetector {
  private typeFingerprints = new Map<string, TypeFingerprint[]>();

  /**
   * 注册类型到全局索引
   */
  register(module: string, type: ParsedType): void {
    const fingerprint = computeFingerprint(type);
    const existing = this.typeFingerprints.get(fingerprint.hash) || [];
    existing.push({ module, type, fingerprint });
    this.typeFingerprints.set(fingerprint.hash, existing);
  }

  /**
   * 识别公共类型（仅在当前版本内）
   * @returns 应该提取到版本内 common/ 的类型列表
   */
  detectCommonTypes(): ParsedType[] {
    const commonTypes: ParsedType[] = [];

    for (const [hash, occurrences] of this.typeFingerprints) {
      // 如果同一类型出现在当前版本的 2+ 个模块中，提取为公共类型
      if (occurrences.length >= 2) {
        const uniqueModules = new Set(occurrences.map(o => o.module));
        if (uniqueModules.size >= 2) {
          commonTypes.push(occurrences[0].type);
        }
      }
    }

    return commonTypes;
  }
}
```

#### 第三阶段：替换引用

```typescript
function replaceWithCommonType(
  type: ParsedType,
  commonTypes: Map<string, string>,
  version: string  // e.g., "1.21.60"
): ParsedType {
  if (commonTypes.has(type.name)) {
    // 替换包名引用到版本内的 common 包
    const versionSegment = `v${version.replace(/\./g, '_')}`;
    return {
      ...type,
      package: `net.easecation.bridge.core.dto.${versionSegment}.common`,
      isCommon: true
    };
  }
  return type;
}
```

**示例**：

```
原始生成计划：
  v1_21_60/behavior/blocks/CollisionBox.java
  v1_21_60/behavior/items/CollisionBox.java  (结构相同)
  v1_21_60/behavior/entities/CollisionBox.java  (结构相同)

公共类型检测后：
  v1_21_60/common/CollisionBox.java  (提取到版本内 common)
  v1_21_60/behavior/blocks/BlockComponents.java  (引用 v1_21_60.common.CollisionBox)
  v1_21_60/behavior/items/ItemComponents.java  (引用 v1_21_60.common.CollisionBox)
```

**生成代码示例**：

```java
// dto/v1_21_60/common/CollisionBox.java
package net.easecation.bridge.core.dto.v1_21_60.common;

/**
 * Collision Box
 * <p>
 * Shared across multiple modules within version 1.21.60 (blocks, items, entities)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CollisionBox(
    @JsonProperty("origin") List<Double> origin,
    @JsonProperty("size") List<Double> size
) {}
```

```java
// dto/v1_21_60/behavior/blocks/BlockComponents.java
package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import net.easecation.bridge.core.dto.v1_21_60.common.CollisionBox;  // ← 引用版本内公共类型

public record BlockComponents(
    @JsonProperty("minecraft:collision_box")
    @Nullable CollisionBox minecraftCollisionBox,
    // ...
) {}
```

---

## 6. 代码生成流程

### 6.1 主流程（一次性生成所有模块）

```typescript
// src/index.ts
async function main(options: GenerateOptions) {
  const version = options.version || detectVersion();  // 例如 "1.21.60"

  console.log(`🚀 开始生成版本 ${version} 的 DTO...`);

  // 1. 加载所有 Schema（一次性加载所有模块）
  const schemaLoader = new SchemaLoader();
  const schemas = await schemaLoader.loadDirectory(
    'schemas/minecraft-bedrock-json-schemas/behavior'
  );

  console.log(`📂 加载了 ${schemas.size} 个 schema 文件`);

  // 2. 解析并注册类型（全局注册）
  const typeRegistry = new TypeRegistry();
  const schemaParser = new SchemaParser(typeRegistry, version);

  for (const [filePath, schema] of schemas) {
    const module = extractModuleName(filePath);  // blocks, items, entities...
    const parsedTypes = schemaParser.parse(schema, { filePath, module, version });
    parsedTypes.forEach(type => typeRegistry.register(module, type));
  }

  console.log(`📝 解析了 ${typeRegistry.size()} 个类型定义`);

  // 3. 公共类型检测（跨模块去重）
  const commonTypeDetector = new CommonTypeDetector();
  for (const [module, types] of typeRegistry.getByModule()) {
    types.forEach(type => commonTypeDetector.register(module, type));
  }

  const commonTypes = commonTypeDetector.detectCommonTypes();
  console.log(`🔍 检测到 ${commonTypes.length} 个公共类型`);

  // 更新类型引用（将重复类型替换为 common 引用）
  typeRegistry.replaceWithCommonTypes(commonTypes);

  // 4. 依赖分析
  const dependencyAnalyzer = new DependencyAnalyzer();
  const sortedTypes = dependencyAnalyzer.analyze(typeRegistry.getAllTypes());

  console.log(`🔗 分析了 ${sortedTypes.length} 个类型的依赖关系`);

  // 5. 生成代码
  const javaGenerator = new JavaGenerator();
  const outputDir = options.outputDir ||
    `addon-bridge-core/src/main/java/net/easecation/bridge/core/dto/v${version.replace(/\./g, '_')}`;
  const fileWriter = new FileWriter(outputDir);

  // 5.1 生成公共类型（到版本内 common 包）
  const commonPackage = `net.easecation.bridge.core.dto.v${version.replace(/\./g, '_')}.common`;
  for (const type of commonTypes) {
    const javaCode = javaGenerator.generate(type);
    fileWriter.write(commonPackage, type.javaClassName, javaCode);
  }

  console.log(`✅ 生成了 ${commonTypes.length} 个版本内公共类型`);

  // 5.2 生成版本化类型
  for (const type of sortedTypes) {
    if (type.isCommon) continue;  // 跳过公共类型
    const javaCode = javaGenerator.generate(type);
    fileWriter.write(type.javaPackage, type.javaClassName, javaCode);
  }

  console.log(`✅ 生成了 ${sortedTypes.length - commonTypes.length} 个版本化类型`);
  console.log(`🎉 总计生成 ${sortedTypes.length} 个 Java 类到 ${outputDir}`);
}
```

### 6.2 CLI 参数设计

```bash
# 生成最新版本（一次性生成所有模块）
npm run generate
# 自动检测 schema 中的 default 版本号（如 1.21.60）

# 指定版本号生成
npm run generate -- --version 1.21.60

# 生成历史版本（配合 git checkout）
cd schemas/minecraft-bedrock-json-schemas
git checkout tags/v1.19.20
cd ../../codegen
npm run generate -- --version 1.19.20

# 指定输出目录
npm run generate -- --output ../addon-bridge-core/src/main/java

# 调试模式（输出中间表示和类型指纹）
npm run generate -- --debug

# 干运行（不写入文件，仅输出统计信息）
npm run generate -- --dry-run

# 清理已生成的文件
npm run generate -- --clean --version 1.21.60
```

**package.json**:
```json
{
  "scripts": {
    "generate": "ts-node src/index.ts",
    "generate:blocks": "ts-node src/index.ts --version 1.21.60",
    "generate:historical": "ts-node src/index.ts --version 1.19.20",
    "dev": "ts-node --watch src/index.ts",
    "build": "tsc",
    "test": "jest"
  }
}
```

**CLI 参数定义**：
```typescript
interface GenerateOptions {
  version?: string;        // 版本号，如 "1.21.60"
  outputDir?: string;      // 输出目录
  debug?: boolean;         // 调试模式
  dryRun?: boolean;        // 干运行
  clean?: boolean;         // 清理已有文件
  schemaDir?: string;      // Schema 目录（默认 schemas/minecraft-bedrock-json-schemas/behavior）
}
```

---

## 7. 特殊场景处理

### 7.1 Minecraft 特有的 Schema 模式

#### 7.1.1 条件 Schema (if-then-else)

**示例** (`loot_tables.json`):
```json
{
  "if": {
    "properties": {
      "condition": { "const": "random_chance" }
    }
  },
  "then": {
    "$ref": "#/definitions/H"
  }
}
```

**处理策略**: 转换为 oneOf 的变体类型

#### 7.1.2 动态属性名 (patternProperties)

**示例**:
```json
{
  "patternProperties": {
    "^[a-zA-Z]+$": {
      "type": "object",
      "properties": { "level": { "type": "integer" } }
    }
  }
}
```

**Java 映射**:
```java
public record Enchantments(
    @JsonAnySetter
    Map<String, EnchantmentLevel> enchantments
) {}
```

使用 `@JsonAnySetter` 捕获动态字段

#### 7.1.3 additionalProperties

**场景**: 允许未定义的额外属性

**Java 映射**:
```java
@JsonIgnoreProperties(ignoreUnknown = true)
public record ComponentsObject(
    // 已知字段...

    @JsonAnySetter
    Map<String, Object> additionalProperties
) {}
```

### 7.2 命名冲突处理

**冲突场景**:
- 同名类（如多个 schema 都有 `Description` 定义）
- Java 保留字（如 `default`, `class`）

**解决方案**:
1. 使用完整路径作为类名前缀（如 `BlockDescription`, `ItemDescription`）
2. Java 保留字添加下划线后缀（如 `default_`）
3. 维护全局命名表，检测并避免冲突

### 7.3 默认值处理

**Schema 定义**:
```json
{
  "properties": {
    "friction": { "type": "number", "default": 0.4 }
  }
}
```

**Java 处理**:
- **选项 1**: 在 record 构造时提供默认值（不推荐，record 无法自定义构造器）
- **选项 2**: 使用 Jackson 的 `@JsonSetter(nulls = Nulls.SKIP)` + 静态工厂方法
- **选项 3**: 在反序列化后处理阶段填充默认值

**推荐**: 选项 3，在生成的代码中添加文档注释说明默认值即可

---

## 8. 测试策略

### 8.1 单元测试

**测试内容**:
- TypeResolver: 各种 schema 模式的类型推断
- JavaNaming: 命名转换正确性
- DependencyAnalyzer: 拓扑排序和循环检测

**工具**: Jest + ts-jest

### 8.2 集成测试

**测试流程**:
1. 生成 Java 代码到临时目录
2. 使用 Gradle 编译生成的 Java 代码
3. 验证编译无错误
4. 使用示例 JSON 文件测试反序列化

**示例**:
```typescript
test('blocks.json 生成的代码可编译', async () => {
  const generator = new CodeGenerator();
  await generator.generate('behavior/blocks/blocks.json', '/tmp/output');

  // 调用 Gradle 编译
  const result = await exec('gradle :addon-bridge-core:compileJava');
  expect(result.exitCode).toBe(0);
});
```

### 8.3 回归测试

**策略**: 保存已生成的 Java 代码作为快照，每次变更后对比差异

---

## 9. 版本管理和升级系统集成

### 9.1 版本迭代工作流

**当前状态**（假设 1.21.60 是最新版本）：
```
dto/
├── v1_16_100/behavior/blocks/...
├── v1_19_20/behavior/blocks/...
└── v1_21_60/behavior/blocks/...  ← 当前最新
```

**当 Minecraft 1.22.0 发布时**：

#### 步骤 1：更新 Schema 仓库
```bash
cd schemas/minecraft-bedrock-json-schemas
git pull origin main  # 获取最新 schema (1.22.0)
```

#### 步骤 2：生成新版本 DTO
```bash
cd ../../codegen
npm run generate -- --version 1.22.0
```

#### 步骤 3：结果
```
dto/
├── v1_16_100/behavior/blocks/...
├── v1_19_20/behavior/blocks/...
├── v1_21_60/behavior/blocks/...  ← 自动变成历史版本
└── v1_22_0/behavior/blocks/...   ← 新生成的最新版本
```

**注意**：
- ✅ 旧版本（v1_21_60）不需要移动或修改，自动成为历史版本
- ✅ 每个版本有独立的公共类型（v1_22_0/common/），不会影响旧版本
- ✅ 所有版本完全独立并存，便于编写 Upgrader 和清理旧版本

### 9.2 生成历史版本 DTO

如果需要生成历史版本（例如补全缺失的 v1_19_20）：

```bash
# 1. 切换 schema 到历史版本
cd schemas/minecraft-bedrock-json-schemas
git checkout tags/v1.19.20

# 2. 生成该版本的 DTO
cd ../../codegen
npm run generate -- --version 1.19.20

# 3. 切换回最新版本
cd schemas/minecraft-bedrock-json-schemas
git checkout main
```

### 9.3 与 Version Upgrade System 集成

根据 `version-upgrade-system-plan.md`，Upgrader 需要引用多个版本的 DTO：

**Upgrader 示例**：
```java
package net.easecation.bridge.core.upgrade.block;

import net.easecation.bridge.core.dto.v1_19_20.behavior.blocks.BlockDefinition as BlockDef_v1_19_20;
import net.easecation.bridge.core.dto.v1_21_60.behavior.blocks.BlockDefinition as BlockDef_v1_21_60;
import net.easecation.bridge.core.upgrade.UpgradeContext;

/**
 * 将 Blocks 从 1.19.20 升级到 1.21.60
 *
 * 主要变更：
 * - 新增 minecraft:liquid_detection 组件
 * - minecraft:transformation 支持 scale_pivot 和 rotation_pivot
 */
public class BlockUpgrader_1_19_20_to_1_21_60
    implements Upgrader<BlockDef_v1_19_20, BlockDef_v1_21_60> {

    @Override
    public BlockDef_v1_21_60 upgrade(BlockDef_v1_19_20 from, UpgradeContext ctx) {
        // 1. 复制基础字段
        var components = upgradeComponents(from.minecraftBlock().components());

        // 2. 返回新版本对象
        return new BlockDef_v1_21_60(
            "1.21.60",  // 更新版本号
            new BlockDef_v1_21_60.MinecraftBlock(
                from.minecraftBlock().description(),
                components,
                from.minecraftBlock().permutations()
            )
        );
    }

    private BlockDef_v1_21_60.BlockComponents upgradeComponents(
        BlockDef_v1_19_20.BlockComponents from
    ) {
        // 转换逻辑...
        return new BlockDef_v1_21_60.BlockComponents(
            from.minecraftCollisionBox(),
            from.minecraftCraftingTable(),
            // ... 其他字段
            null  // minecraft:liquid_detection (新字段，默认 null)
        );
    }
}
```

**Upgrader 注册**：
```java
// UpgraderRegistry.java
public class UpgraderRegistry {
    private static final Map<VersionPair, Upgrader<?, ?>> UPGRADERS = Map.of(
        new VersionPair("1.16.100", "1.19.20"),
            new BlockUpgrader_1_16_100_to_1_19_20(),
        new VersionPair("1.19.20", "1.21.60"),
            new BlockUpgrader_1_19_20_to_1_21_60()
    );

    public <F, T> Upgrader<F, T> getUpgrader(String fromVersion, String toVersion) {
        return (Upgrader<F, T>) UPGRADERS.get(new VersionPair(fromVersion, toVersion));
    }
}
```

### 9.4 版本号管理

在 Java 代码中维护版本常量：

```java
// dto/KnownVersions.java
package net.easecation.bridge.core.dto;

/**
 * 已知的 Minecraft Bedrock format_version 版本
 */
public final class KnownVersions {
    public static final String V1_16_100 = "1.16.100";
    public static final String V1_19_20 = "1.19.20";
    public static final String V1_21_60 = "1.21.60";

    /**
     * 当前最新支持的版本
     */
    public static final String LATEST = V1_21_60;

    /**
     * 所有支持的版本（按时间顺序）
     */
    public static final List<String> ALL_VERSIONS = List.of(
        V1_16_100,
        V1_19_20,
        V1_21_60
    );
}
```

### 9.5 DTO 中的版本标注

每个版本的 DTO 包中添加版本标识：

```java
// dto/v1_21_60/package-info.java
/**
 * Minecraft Bedrock Add-on DTOs for format version 1.21.60
 * <p>
 * Generated by json-schema-to-java-codegen from
 * minecraft-bedrock-json-schemas (commit: abc123def)
 *
 * @version 1.21.60
 * @since 2025-10-28
 */
@NonNullApi  // 默认参数不为 null
package net.easecation.bridge.core.dto.v1_21_60;

import org.springframework.lang.NonNullApi;
```

---

## 10. 开发计划

### 阶段 1: 基础框架搭建 (3-5天)
- [x] 初始化 TypeScript 项目
- [ ] 实现 SchemaLoader（基于 json-schema-ref-parser）
- [ ] 实现 TypeRegistry（支持按模块注册）
- [ ] 实现 DependencyAnalyzer
- [ ] 实现 CommonTypeDetector（公共类型检测）
- [ ] 编写基础工具函数（JavaNaming, FileWriter）

### 阶段 2: 核心解析器实现 (5-7天)
- [ ] 实现 SchemaParser 基础功能（支持版本参数）
- [ ] 处理基本类型映射
- [ ] 处理 object 类型（生成 record）
- [ ] 处理 oneOf/anyOf（生成 sealed interface）
- [ ] 处理 allOf（属性合并）
- [ ] 处理 enum（生成 Java enum）
- [ ] 处理 $ref 引用

### 阶段 3: 通用代码生成器实现 (5-7天)
- [ ] 实现 JavaGenerator（通用生成器，无模块特定逻辑）
- [ ] 实现 TemplateEngine（Handlebars 封装）
- [ ] 实现 AnnotationBuilder（Jackson 注解构建）
- [ ] 实现 JavadocConverter（description 转换）
- [ ] 编写 Handlebars 模板（Record, SealedInterface, Enum）
- [ ] 处理特殊场景（patternProperties, additionalProperties）
- [ ] 实现公共类型生成到版本内 common/ 包
- [ ] 测试生成器对不同 Schema 模式的通用性

### 阶段 4: 一次性生成所有模块 (7-10天)
- [ ] 实现批量 Schema 加载（所有模块）
- [ ] 实现类型指纹计算
- [ ] 实现公共类型检测算法
- [ ] 适配 blocks.json（最复杂的 schema 之一）
- [ ] 适配 items.json
- [ ] 适配 entities.json
- [ ] 适配 loot_tables.json
- [ ] 适配其他 18 个 schema
- [ ] 处理 schema 特有的边界情况

### 阶段 5: 版本管理实现 (3-4天)
- [ ] 实现版本号解析和包名生成
- [ ] 实现顶层版本目录结构
- [ ] 生成 package-info.java 和版本标识
- [ ] 生成 KnownVersions.java 常量类
- [ ] 测试多版本并存场景

### 阶段 6: 测试和优化 (3-5天)
- [ ] 编写单元测试（覆盖率 > 80%）
- [ ] 编译生成的 Java 代码并修复错误
- [ ] 编写集成测试（使用真实 JSON 测试反序列化）
- [ ] 测试历史版本生成（git checkout 到旧版本）
- [ ] 性能优化（大型 schema 的生成速度）
- [ ] 代码质量优化（重构、文档）

### 阶段 7: 文档和交付 (2-3天)
- [ ] 编写使用文档（README）
- [ ] 编写开发者文档（架构、扩展指南）
- [ ] 编写版本管理指南
- [ ] 代码示例和最佳实践
- [ ] 整合到项目构建流程（Gradle task）

**预计总工期**: 28-42 天（约 4-6 周）

---

## 10. 未来扩展

### 10.1 资源包（Resource Pack）支持
- 扩展到 `schemas/minecraft-bedrock-json-schemas/resource` 目录
- 生成到 `dto.resource` 包

### 10.2 增量生成
- 仅重新生成变更的 schema
- 基于文件哈希的缓存机制

### 10.3 代码验证
- 集成 CheckStyle 或 SpotBugs
- 自动格式化（Google Java Format）

### 10.4 IDE 集成
- 生成 IntelliJ IDEA 插件，提供 JSON → Java 的实时预览

---

## 11. 风险和挑战

### 11.1 Schema 复杂性
**风险**: Minecraft 的 schema 使用了大量高级特性（if-then-else, patternProperties, 深度嵌套的 $ref）

**应对**:
- 采用增量开发，先支持核心场景（80% 的常见模式）
- 对复杂场景提供手动调整的机制（生成后修改）
- 持续迭代，逐步覆盖边界情况

### 11.2 Jackson 兼容性
**风险**: sealed class 的多态反序列化可能需要自定义 Deserializer

**应对**:
- 优先使用 Jackson 原生支持的注解
- 为复杂场景自动生成自定义 Deserializer
- 提供配置选项（使用 DEDUCTION 或自定义反序列化器）

### 11.3 命名冲突
**风险**: 多个 schema 可能定义同名类型

**应对**:
- 使用文件前缀作为命名空间（如 `BlocksDescription`, `ItemsDescription`）
- 维护全局命名注册表，检测冲突并报错

### 11.4 循环依赖
**风险**: 类型 A 引用 B，B 引用 A

**应对**:
- DependencyAnalyzer 检测环并报错
- 对于合理的循环（接口互引用），使用 sealed interface 解决

---

## 12. 参考资料

- [JSON Schema Specification](https://json-schema.org/specification.html)
- [Jackson Annotations](https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations)
- [Java 17 Sealed Classes](https://openjdk.org/jeps/409)
- [json-schema-ref-parser Documentation](https://apitools.dev/json-schema-ref-parser/)
- [Minecraft Bedrock Add-on Documentation](https://learn.microsoft.com/en-us/minecraft/creator/)

---

## 附录 A: 类型映射速查表

| JSON Schema | Java 类型 | Jackson 注解 | 备注 |
|-------------|-----------|-------------|------|
| `type: "string"` | `String` | - | - |
| `type: "integer"` | `Integer` | - | 考虑范围使用 `Long` |
| `type: "number"` | `Double` | - | 考虑精度使用 `Float` |
| `type: "boolean"` | `Boolean` | - | - |
| `type: "array"` | `List<T>` | - | - |
| `type: ["string", "null"]` | `@Nullable String` | `@JsonProperty` | 可选字段 |
| `enum: [...]` | `enum` | `@JsonProperty` | 枚举值映射 |
| `oneOf: [...]` | `sealed interface` | `@JsonTypeInfo` | 多态类型 |
| `allOf: [...]` | 合并 record | - | 属性合并 |
| `$ref: "#/def/X"` | `X` | - | 类型引用 |
| `patternProperties` | `Map<String, T>` | `@JsonAnySetter` | 动态字段 |
| `additionalProperties` | `Map<String, Object>` | `@JsonAnySetter` | 额外属性 |

---

## 附录 B: 示例输出

### 输入: `behavior/blocks/blocks.json`

### 输出: `net.easecation.bridge.core.dto.behavior.blocks.BlockDefinition.java`

```java
package net.easecation.bridge.core.dto.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Block Behavior
 * <p>
 * The minecraft block behavior specification.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BlockDefinition(
    /**
     * Format Version
     * <p>
     * A version that tells minecraft what type of data format can be expected when reading this file.
     */
    @JsonProperty("format_version")
    String formatVersion,

    /**
     * Block Definitions
     */
    @JsonProperty("minecraft:block")
    MinecraftBlock minecraftBlock
) {

    /**
     * Block Definitions
     * <p>
     * A custom block definition.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record MinecraftBlock(
        @JsonProperty("description")
        Description description,

        @JsonProperty("components")
        Components components,

        @JsonProperty("permutations")
        @Nullable List<Permutation> permutations
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Description(
        /**
         * Identifier
         * <p>
         * The identifier for this block. The name must include a namespace and must not use the Minecraft namespace unless overriding a Vanilla block.
         */
        @JsonProperty("identifier")
        String identifier,

        @JsonProperty("menu_category")
        @Nullable MenuCategory menuCategory
    ) {}

    // ... 其他嵌套类型
}
```

---

## 13. 总结

本文档详细描述了 JSON Schema 到 Java DTO 代码生成器的技术方案，核心亮点包括：

1. **版本化架构**：顶层版本目录，支持多版本 DTO 并存，每个版本完全独立
2. **一次性生成**：单次运行生成所有模块，全局分析版本内公共类型
3. **版本内公共类型去重**：通过类型指纹识别同一版本内跨模块重复类型
4. **与升级系统集成**：生成的 DTO 可直接用于版本升级器
5. **Git 工作流友好**：通过 git checkout 轻松生成历史版本

该方案确保了代码生成的通用性、可维护性和可扩展性，为 Minecraft 基岩版 Add-on 的解析和版本管理提供了坚实的基础。

---

**文档版本**: v2.0
**最后更新**: 2025-10-28
**作者**: Claude Code + fangyizhou
**变更日志**:
- v2.1 (2025-10-28): 移除模块特定生成器，改用完全通用的 JavaGenerator，强调零硬编码原则
- v2.0 (2025-10-28): 添加版本管理策略、版本内公共类型去重、与升级系统集成
- v1.0 (2025-10-28): 初始版本，基础架构和代码生成流程
