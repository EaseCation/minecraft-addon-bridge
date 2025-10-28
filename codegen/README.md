# JSON Schema to Java DTO Code Generator

从 Minecraft 基岩版 JSON Schema 自动生成类型安全的 Java DTO 类，支持 Jackson 序列化/反序列化。

## 功能特性

- **Schema 驱动**：完全基于 JSON Schema 定义生成代码，零硬编码
- **类型安全**：使用 Java 17 特性（record、sealed interface、枚举）
- **多版本支持**：独立生成不同版本的 DTO，便于版本升级系统
- **智能类型推断**：自动处理 oneOf/anyOf（多态）、allOf（合并）、enum 等复杂模式
- **内联嵌套**：自动检测并生成嵌套类型，避免文件泛滥
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

```bash
npm run generate
```

生成的 Java 代码位于：
```
addon-bridge-core/src/main/java/net/easecation/bridge/core/dto/v1_21_60/
```

## 目录结构

```
codegen/
├── src/
│   ├── core/                    # 核心引擎
│   │   ├── SchemaLoader.ts      # Schema 加载和 $ref 解析
│   │   └── TypeRegistry.ts      # 全局类型注册表
│   ├── parsers/                 # 解析器层
│   │   ├── SchemaParser.ts      # 主解析器入口
│   │   ├── TypeResolver.ts      # 类型解析和映射
│   │   └── types.ts             # 解析结果的 TypeScript 类型定义
│   ├── generator/               # 代码生成器层
│   │   ├── JavaGenerator.ts     # 通用 Java 代码生成器
│   │   ├── TemplateEngine.ts    # Handlebars 模板引擎封装
│   │   └── AnnotationBuilder.ts # Jackson 注解构建器
│   ├── templates/               # Handlebars 模板
│   │   ├── Record.hbs           # Java record 模板
│   │   ├── Class.hbs            # Java class 模板（字段过多时）
│   │   ├── SealedInterface.hbs  # sealed interface 模板（多态类型）
│   │   ├── Enum.hbs             # Java enum 模板
│   │   └── partials/            # 模板片段
│   ├── utils/                   # 工具函数
│   │   ├── JavaNaming.ts        # Java 命名转换
│   │   ├── JavadocConverter.ts  # Markdown 到 Javadoc 转换
│   │   └── FileWriter.ts        # 文件写入工具
│   └── index.ts                 # CLI 入口
├── tsconfig.json
├── package.json
└── README.md
```

## 生成流程

```
JSON Schema 文件
    ↓
SchemaLoader（解析 $ref）
    ↓
SchemaParser（解析结构）
    ↓
TypeRegistry（注册类型定义）
    ↓
JavaGenerator（生成代码）
    ↓
FileWriter（写入 .java 文件）
    ↓
Gradle compileJava（编译验证）
```

## 类型映射规则

| JSON Schema | Java 类型 | 说明 |
|-------------|-----------|------|
| `type: "string"` | `String` | - |
| `type: "integer"` | `Integer` | - |
| `type: "number"` | `Double` | - |
| `type: "boolean"` | `Boolean` | - |
| `type: "array"` | `List<T>` | 根据 `items` 确定 `T` |
| `type: "object"` | `record` 或嵌套类 | 根据属性数量和结构决定 |
| `enum: [...]` | `enum` | Java 枚举类 |
| `oneOf: [...]` | `sealed interface` | 多态类型（Jackson DEDUCTION） |
| `allOf: [...]` | 合并属性 | 展平到单个 record |
| `additionalProperties` | `Map<String, T>` | 动态属性 |

## CLI 参数

```bash
# 生成最新版本
npm run generate

# 指定版本号
npm run generate -- --version 1.21.60

# 指定输出目录
npm run generate -- --output ../addon-bridge-core/src/main/java

# 调试模式（输出详细日志）
npm run generate -- --debug

# 干运行（不写入文件）
npm run generate -- --dry-run
```

## 版本管理

生成器支持多版本 DTO 并存：

```
dto/
├── v1_16_100/behavior/...    # 历史版本
├── v1_19_20/behavior/...     # 历史版本
└── v1_21_60/behavior/...     # 当前版本
```

### 生成历史版本

```bash
# 1. 切换 Schema 到历史版本
cd schemas/minecraft-bedrock-json-schemas
git checkout tags/v1.19.20

# 2. 生成历史版本 DTO
cd ../../codegen
npm run generate -- --version 1.19.20

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
package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/** Block Behavior */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Block(
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

## 特殊处理

### 多态类型（oneOf/anyOf）

Schema：
```json
{
  "oneOf": [
    { "type": "string" },
    { "type": "object", "properties": { "min": {...}, "max": {...} } }
  ]
}
```

生成：
```java
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Value {
    record StringValue(String value) implements Value {}
    record RangeValue(Integer min, Integer max) implements Value {}
}
```

### 空对象语义

显式空 Schema（`{}`）生成为全局单例 `EmptyObject`，避免与 `Object` 混淆。

### 可选字段

Schema 中未列入 `required` 的字段自动标记 `@Nullable`。

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

**Q: 为什么某些字段是 `Object` 类型？**

A: 可能原因：
1. Schema 中 `items` 未定义（无法推断数组元素类型）
2. 多态类型（anyOf）过于复杂，无法安全映射
3. Schema 定义不完整

**Q: 如何更新到新版本的 Minecraft Schema？**

A:
```bash
cd schemas/minecraft-bedrock-json-schemas
git pull origin main
cd ../../codegen
npm run generate
```

**Q: 生成的代码编译失败怎么办？**

A: 生成器会自动调用 Gradle 编译验证。检查错误日志，常见问题：
- 类名冲突（Schema 中同名类型）
- 循环依赖（需要手动调整）
- Jackson 注解问题（检查版本兼容性）

**Q: 如何为新的 Schema 字段添加支持？**

A: 修改 `SchemaParser.ts` 和 `TypeResolver.ts`，添加新的解析规则。无需修改模板（除非需要新的代码结构）。

## 参考资料

- [JSON Schema 规范](https://json-schema.org/specification.html)
- [Jackson 注解文档](https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations)
- [Java 17 Sealed Classes](https://openjdk.org/jeps/409)
- [Minecraft Bedrock 文档](https://learn.microsoft.com/en-us/minecraft/creator/)

## 技术方案

完整的技术方案和架构设计见：[docs/json-schema-to-java-codegen-plan.md](../docs/json-schema-to-java-codegen-plan.md)

## License

MIT
