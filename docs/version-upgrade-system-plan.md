# Bedrock Addons 版本兼容与升级系统设计方案

> **目标**：设计并实现一个类似 Minecraft Bedrock 引擎的版本兼容系统，能够将不同 `format_version` 的 JSON 数据逐层升级到最新的内部格式，确保核心代码只需处理单一最新版本。

---

## 1. 背景与问题分析

### 1.1 Bedrock Addons 的版本机制

Minecraft Bedrock Edition 的行为包和资源包使用 `format_version` 字段标识 JSON 格式版本：

```json
{
  "format_version": "1.21.60",
  "minecraft:block": {
    "description": { ... },
    "components": { ... }
  }
}
```

**版本演变示例**：
- **1.16.100**：使用 `minecraft:destroy_time` 表示硬度
- **1.19.20**：引入 `minecraft:destructible_by_mining`，废弃 `destroy_time`
- **1.20.60**：光照字段从整数改为浮点数，字段名也发生变化

### 1.2 当前实现的问题（ECProEntity）

**问题 1**：无视 `format_version`，解析时直接映射到固定的 Java 类
```java
// EntityBehaviourFile.java
public record EntityBehaviourFile(
    String format_version,  // ← 读取了但未使用
    @SerializedName("minecraft:entity") EntityBehaviour minecraft_entity
) {}
```

**问题 2**：在输出时同时生成多个版本的字段（硬编码兼容）
```java
// BlockComponents.java toNBT() 方法
minecraft_light_emission.ifPresent(i -> {
    tag.putCompound("minecraft:block_light_emission", ...);  // 1.12.0 格式
    tag.putCompound("minecraft:light_emission", ...);        // 1.19.20 格式
});
```
这种做法：
- 没有真正的"版本升级"逻辑，只是同时输出多个字段
- 核心代码需要处理多个版本的字段变体
- 无法处理字段语义变化（如字段类型、嵌套结构改变）

**问题 3**：无法应对复杂的版本变更
- 字段重命名：`minecraft:destroy_time` → `minecraft:destructible_by_mining`
- 类型变化：`light_emission: int` → `light_emission: float`
- 结构重组：单个字段 → 嵌套对象
- 联合类型演变：`boolean` → `boolean | object`

---

## 2. 设计目标

### 2.1 核心原则

1. **单一真理源（Single Source of Truth）**
   核心业务代码只处理**最新版本**的数据模型，旧版本数据通过升级链自动转换

2. **渐进式升级（Incremental Upgrade）**
   定义版本间的增量升级器（Upgrader），自动构建升级链：
   `1.16.100 → 1.19.20 → 1.20.60 → 1.21.60`

3. **可扩展性（Extensibility）**
   新增版本时只需添加新的升级器，无需修改已有代码

4. **透明性（Transparency）**
   升级过程对使用方透明，Jackson 反序列化后直接得到最新版本的模型

### 2.2 代码组织结构

```
addon-bridge-core/
├── src/main/java/net/easecation/bridge/core/
│   ├── model/                          # 最新版本的核心模型（无版本后缀）
│   │   ├── block/
│   │   │   ├── BlockDef.java
│   │   │   ├── BlockDescription.java
│   │   │   ├── BlockComponents.java
│   │   │   └── component/              # 各个组件定义
│   │   │       ├── DestructibleByMining.java
│   │   │       ├── CollisionBox.java
│   │   │       └── Geometry.java
│   │   ├── entity/
│   │   │   ├── EntityDef.java
│   │   │   └── EntityComponents.java
│   │   ├── item/
│   │   └── recipe/
│   │
│   ├── version/                        # 版本系统
│   │   ├── FormatVersion.java
│   │   ├── KnownVersions.java
│   │   └── VersionRange.java
│   │
│   ├── upgrade/                        # 升级系统核心
│   │   ├── Upgrader.java
│   │   ├── UpgradeContext.java
│   │   ├── UpgradeChain.java
│   │   ├── UpgraderRegistry.java
│   │   └── UpgradeException.java
│   │
│   ├── versioned/                      # 历史版本的 DTO
│   │   ├── v1_16_100/
│   │   │   ├── block/
│   │   │   │   ├── BlockDef_v1_16_100.java
│   │   │   │   ├── BlockDescription_v1_16_100.java
│   │   │   │   └── BlockComponents_v1_16_100.java
│   │   │   ├── entity/
│   │   │   └── item/
│   │   │
│   │   ├── v1_19_20/
│   │   │   ├── block/
│   │   │   ├── entity/
│   │   │   └── item/
│   │   │
│   │   ├── v1_20_60/
│   │   └── v1_21_60/                   # 最新版本（与 model/ 保持同步）
│   │
│   ├── upgraders/                      # 升级器实现
│   │   ├── block/
│   │   │   ├── BlockUpgrader_1_16_100_to_1_19_20.java
│   │   │   ├── BlockUpgrader_1_19_20_to_1_20_60.java
│   │   │   └── BlockUpgrader_1_20_60_to_1_21_60.java
│   │   ├── entity/
│   │   │   ├── EntityUpgrader_1_16_0_to_1_20_0.java
│   │   │   └── EntityUpgrader_1_20_0_to_1_21_60.java
│   │   └── UpgraderRegistration.java   # 统一注册入口
│   │
│   └── jackson/                        # Jackson 集成
│       ├── VersionAwareModule.java     # Jackson Module
│       ├── BlockDefDeserializer.java
│       ├── EntityDefDeserializer.java
│       └── Mappers.java
│
└── src/test/java/
    ├── upgrade/                        # 升级器单元测试
    │   ├── BlockUpgraderTest.java
    │   └── EntityUpgraderTest.java
    └── integration/                    # 集成测试（真实 JSON）
        └── RealAddonTest.java
```

**包结构说明**：
- **`model/`**：最新版本的模型，核心业务逻辑使用，**无版本后缀**
- **`versioned/`**：历史版本的 DTO，按版本号组织，**带版本后缀**
- **`upgraders/`**：升级器实现，按类型（block/entity/item）组织
- **`version/`**、`upgrade/`**：基础设施代码，与具体版本无关
- **`jackson/`**：Jackson 集成层，负责解析 + 自动升级

### 2.2 功能需求

| 功能 | 描述 |
|------|------|
| **版本检测** | 从 `format_version` 字段识别 JSON 版本 |
| **升级链构建** | 自动找到从旧版本到最新版本的升级路径 |
| **字段迁移** | 处理重命名、类型转换、默认值填充 |
| **结构重组** | 支持嵌套层级变化、联合类型演变 |
| **降级支持**（可选）| 为了输出兼容性，支持从新版本降级到旧版本 |
| **日志与诊断** | 记录升级过程，便于调试和问题定位 |

---

## 3. 架构设计

### 3.1 整体架构

```
┌─────────────────────────────────────────────────────────────┐
│                        JSON 输入                             │
│  { "format_version": "1.19.20", "minecraft:block": {...} }  │
└──────────────────────┬──────────────────────────────────────┘
                       │ Jackson 解析
                       ▼
┌─────────────────────────────────────────────────────────────┐
│              VersionAwareDeserializer                        │
│  ① 提取 format_version                                       │
│  ② 解析为对应版本的 DTO (BlockDef_v1_19_20)                  │
└──────────────────────┬──────────────────────────────────────┘
                       │ 传递给升级系统
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                  UpgradeChain 升级链                         │
│  BlockUpgrader_v1_19_20_to_v1_20_60                         │
│         ↓                                                    │
│  BlockUpgrader_v1_20_60_to_v1_21_60                         │
└──────────────────────┬──────────────────────────────────────┘
                       │ 输出最新版本
                       ▼
┌─────────────────────────────────────────────────────────────┐
│              最新版本的中间模型                              │
│         BlockDef (对应 format_version 1.21.60)              │
└─────────────────────────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│              核心业务逻辑                                    │
│  只处理最新版本，无需关心历史兼容性                          │
└─────────────────────────────────────────────────────────────┘
```

### 3.2 核心组件

#### 3.2.1 版本定义（Version）

```java
/**
 * 表示 Bedrock format_version
 * 支持格式：[major, minor, patch] 或 "major.minor.patch"
 */
public record FormatVersion(int major, int minor, int patch)
    implements Comparable<FormatVersion> {

    /**
     * 从字符串解析版本号
     * @param version "1.21.60" 或 "1.21" (patch 默认为 0)
     */
    public static FormatVersion parse(String version) {
        String[] parts = version.split("\\.");
        int major = Integer.parseInt(parts[0]);
        int minor = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        int patch = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
        return new FormatVersion(major, minor, patch);
    }

    /**
     * 从数组解析版本号（Manifest 中的格式）
     * @param version [1, 21, 60] 或 [1, 21]
     */
    public static FormatVersion parse(List<Integer> version) {
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException("Version list cannot be empty");
        }
        int major = version.get(0);
        int minor = version.size() > 1 ? version.get(1) : 0;
        int patch = version.size() > 2 ? version.get(2) : 0;
        return new FormatVersion(major, minor, patch);
    }

    @Override
    public int compareTo(FormatVersion other) {
        int cmp = Integer.compare(this.major, other.major);
        if (cmp != 0) return cmp;
        cmp = Integer.compare(this.minor, other.minor);
        if (cmp != 0) return cmp;
        return Integer.compare(this.patch, other.patch);
    }

    public String toVersionString() {
        return major + "." + minor + "." + patch;
    }

    /**
     * 判断是否为 Beta 版本（我们不支持）
     */
    public boolean isBeta() {
        // Beta 版本通常在 minor 或 patch 中有特殊标记，或者在字符串中包含 "beta"
        // 当前设计：所有正式版本号都是纯数字，不支持 Beta
        return false;  // 保留接口，未来可扩展
    }
}
```

**已知版本常量**（参考 bedrock-dot-dev 文档）：
```java
public final class KnownVersions {

    // ====== Block 相关版本 ======
    /**
     * Minecraft 1.16.100 (2020-11)
     * - 首次引入自定义方块
     * - 使用 minecraft:destroy_time 表示硬度
     * - 光照使用整数 0-15
     */
    public static final FormatVersion BLOCK_V1_16_100 = new FormatVersion(1, 16, 100);

    /**
     * Minecraft 1.19.20 (2022-08)
     * - 引入 minecraft:destructible_by_mining 替代 destroy_time
     * - 新增 item_specific_speeds 支持
     */
    public static final FormatVersion BLOCK_V1_19_20 = new FormatVersion(1, 19, 20);

    /**
     * Minecraft 1.20.60 (2024-01)
     * - 光照字段从整数改为浮点数
     * - minecraft:light_emission (int) → minecraft:block_light_emission (float)
     * - 光照范围从 0-15 改为 0.0-1.0
     */
    public static final FormatVersion BLOCK_V1_20_60 = new FormatVersion(1, 20, 60);

    /**
     * Minecraft 1.21.60 (2025 预计)
     * - 当前最新版本
     * - 优化碰撞盒和选择盒的计算
     */
    public static final FormatVersion BLOCK_V1_21_60 = new FormatVersion(1, 21, 60);

    /**
     * Block 类型支持的最早版本（设计决策）
     */
    public static final FormatVersion BLOCK_MIN_SUPPORTED = BLOCK_V1_16_100;

    /**
     * Block 类型的最新版本（核心模型对应的版本）
     */
    public static final FormatVersion BLOCK_LATEST = BLOCK_V1_21_60;

    // ====== Entity 相关版本 ======
    /**
     * Minecraft 1.16.0 (2020-06)
     * - 首次支持自定义实体
     */
    public static final FormatVersion ENTITY_V1_16_0 = new FormatVersion(1, 16, 0);

    /**
     * Minecraft 1.20.0 (2023-06)
     * - 新增多个实体组件
     */
    public static final FormatVersion ENTITY_V1_20_0 = new FormatVersion(1, 20, 0);

    /**
     * Minecraft 1.21.60 (当前最新)
     */
    public static final FormatVersion ENTITY_V1_21_60 = new FormatVersion(1, 21, 60);

    public static final FormatVersion ENTITY_MIN_SUPPORTED = ENTITY_V1_16_0;
    public static final FormatVersion ENTITY_LATEST = ENTITY_V1_21_60;

    // ====== Item 相关版本 ======
    public static final FormatVersion ITEM_V1_16_100 = new FormatVersion(1, 16, 100);
    public static final FormatVersion ITEM_V1_21_60 = new FormatVersion(1, 21, 60);
    public static final FormatVersion ITEM_MIN_SUPPORTED = ITEM_V1_16_100;
    public static final FormatVersion ITEM_LATEST = ITEM_V1_21_60;

    // ====== Recipe 相关版本 ======
    public static final FormatVersion RECIPE_V1_16_0 = new FormatVersion(1, 16, 0);
    public static final FormatVersion RECIPE_V1_21_60 = new FormatVersion(1, 21, 60);
    public static final FormatVersion RECIPE_MIN_SUPPORTED = RECIPE_V1_16_0;
    public static final FormatVersion RECIPE_LATEST = RECIPE_V1_21_60;

    /**
     * 检查版本是否在支持范围内
     */
    public static boolean isSupported(String type, FormatVersion version) {
        FormatVersion min = switch (type) {
            case "block" -> BLOCK_MIN_SUPPORTED;
            case "entity" -> ENTITY_MIN_SUPPORTED;
            case "item" -> ITEM_MIN_SUPPORTED;
            case "recipe" -> RECIPE_MIN_SUPPORTED;
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
        return version.compareTo(min) >= 0;
    }
}
```

**版本支持策略**：
- ✅ **最早支持版本**：1.16.100（2020 年 11 月），这是 Bedrock 首次稳定支持自定义方块/实体的版本
- ❌ **不支持 Beta 版本**：Beta 版本的 format_version 格式不稳定，且社区使用率低
- 🔄 **版本兼容性**：低于最早支持版本的 JSON 将被拒绝，记录错误日志

#### 3.2.2 升级器接口（Upgrader）

```java
/**
 * 版本升级器：从版本 F 升级到版本 T
 */
@FunctionalInterface
public interface Upgrader<F, T> {

    /**
     * 执行升级
     * @param from 旧版本数据
     * @param context 升级上下文（可选，用于传递额外信息）
     * @return 新版本数据
     */
    T upgrade(F from, UpgradeContext context);

    /**
     * 源版本
     */
    default FormatVersion sourceVersion() {
        // 通过泛型或注解获取
    }

    /**
     * 目标版本
     */
    default FormatVersion targetVersion() {
        // 通过泛型或注解获取
    }
}
```

**升级上下文**（携带额外信息）：
```java
public class UpgradeContext {
    private final FormatVersion originalVersion;  // 原始输入版本
    private final FormatVersion targetVersion;    // 最终目标版本
    private final Map<String, Object> metadata;   // 自定义元数据

    public void logWarning(String message) {
        // 记录升级过程中的警告
    }

    public void addMigrationNote(String note) {
        // 记录迁移说明（如字段被废弃）
    }
}
```

#### 3.2.3 升级链（UpgradeChain）

```java
/**
 * 管理和执行升级链
 */
public class UpgradeChain<T> {

    private final List<Upgrader<?, ?>> upgraders;

    /**
     * 自动构建从 sourceVersion 到 targetVersion 的升级链
     */
    public static <T> UpgradeChain<T> build(
        FormatVersion sourceVersion,
        FormatVersion targetVersion,
        Class<T> targetType
    ) {
        // 使用图算法（如 Dijkstra）找到最短升级路径
        // 例如：1.16.100 → 1.19.20 → 1.21.60
    }

    /**
     * 执行升级
     */
    public T execute(Object source, UpgradeContext context) {
        Object current = source;
        for (Upgrader<?, ?> upgrader : upgraders) {
            current = ((Upgrader<Object, Object>) upgrader).upgrade(current, context);
        }
        return (T) current;
    }
}
```

#### 3.2.4 升级器注册表（UpgraderRegistry）

```java
/**
 * 全局升级器注册表
 */
public class UpgraderRegistry {

    // 按类型分组：block, entity, item, recipe, etc.
    private final Map<String, List<Upgrader<?, ?>>> upgradersByType = new HashMap<>();

    /**
     * 注册升级器
     */
    public void register(String type, Upgrader<?, ?> upgrader) {
        upgradersByType.computeIfAbsent(type, k -> new ArrayList<>()).add(upgrader);
    }

    /**
     * 查找升级路径
     */
    public List<Upgrader<?, ?>> findUpgradePath(
        String type,
        FormatVersion from,
        FormatVersion to
    ) {
        // BFS 查找最短路径
    }

    /**
     * 初始化时注册所有已知升级器
     */
    static {
        // Block 升级器
        register("block", new BlockUpgrader_1_16_100_to_1_19_20());
        register("block", new BlockUpgrader_1_19_20_to_1_20_60());
        register("block", new BlockUpgrader_1_20_60_to_1_21_60());

        // Entity 升级器
        register("entity", new EntityUpgrader_1_16_0_to_1_20_0());
        register("entity", new EntityUpgrader_1_20_0_to_1_21_60());

        // ... 其他类型
    }
}
```

---

## 4. 与 Jackson 集成

### 4.1 自定义反序列化器

```java
/**
 * 版本感知的 Block 反序列化器
 */
public class BlockDefDeserializer extends JsonDeserializer<BlockDef> {

    private static final UpgraderRegistry REGISTRY = UpgraderRegistry.getInstance();

    @Override
    public BlockDef deserialize(JsonParser p, DeserializationContext ctx)
        throws IOException {

        JsonNode root = p.getCodec().readTree(p);

        // 1. 提取 format_version
        FormatVersion version = FormatVersion.parse(
            root.path("format_version").asText("1.16.100")
        );

        // 2. 根据版本解析为对应的 DTO
        Object versionedData = parseByVersion(root, version);

        // 3. 如果不是最新版本，执行升级
        if (version.compareTo(KnownVersions.BLOCK_LATEST) < 0) {
            UpgradeContext context = new UpgradeContext(version, KnownVersions.BLOCK_LATEST);
            UpgradeChain<BlockDef> chain = REGISTRY.buildChain(
                "block", version, KnownVersions.BLOCK_LATEST, BlockDef.class
            );
            return chain.execute(versionedData, context);
        }

        return (BlockDef) versionedData;
    }

    private Object parseByVersion(JsonNode root, FormatVersion version) {
        // 根据版本选择对应的 DTO 类
        if (version.equals(KnownVersions.BLOCK_V1_16_100)) {
            return Mappers.MAPPER.treeToValue(root, BlockDef_v1_16_100.class);
        } else if (version.equals(KnownVersions.BLOCK_V1_19_20)) {
            return Mappers.MAPPER.treeToValue(root, BlockDef_v1_19_20.class);
        }
        // ... 其他版本
        // 默认使用最新版本
        return Mappers.MAPPER.treeToValue(root, BlockDef.class);
    }
}
```

**使用方式**：
```java
// 在 ObjectMapper 中注册
SimpleModule module = new SimpleModule();
module.addDeserializer(BlockDef.class, new BlockDefDeserializer());
Mappers.MAPPER.registerModule(module);

// 解析时自动升级到最新版本
BlockDef block = Mappers.MAPPER.readValue(jsonFile, BlockDef.class);
// 无论输入是 1.16.100 还是 1.19.20，都会得到最新版本的 BlockDef
```

### 4.2 版本化 DTO 设计

#### 4.2.1 命名规范（设计决策）

**✅ 保留 JSON 原始命名（snake_case）**

```java
// ✅ 正确：使用 snake_case，与 JSON 保持一致
public record BlockComponents(
    Optional<Float> minecraft_destroy_time,
    Optional<Integer> minecraft_light_emission,
    Optional<DestructibleByMining> minecraft_destructible_by_mining
) {}

// ❌ 错误：不使用 camelCase
public record BlockComponents(
    Optional<Float> minecraftDestroyTime,
    Optional<Integer> minecraftLightEmission
) {}
```

**理由**：
1. **可读性**：字段名与 JSON 键名一一对应，降低理解成本
2. **调试友好**：错误信息中的字段名可直接定位到 JSON 文件
3. **工具支持**：Jackson `@JsonProperty` 注解可省略（字段名自动匹配）
4. **社区一致性**：Bedrock 文档和社区代码都使用 snake_case

**Jackson 配置**：
```java
// ObjectMapper 配置（保持默认即可）
ObjectMapper mapper = new ObjectMapper()
    .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);  // 可选，字段名已匹配

// DTO 定义无需额外注解
public record BlockComponents(
    Optional<Float> minecraft_destroy_time  // 自动映射到 "minecraft:destroy_time"
) {}
```

#### 4.2.2 DTO 结构设计

**推荐：每个版本独立的 DTO 类**

```java
// ====== v1.16.100 版本 ======
public record BlockDef_v1_16_100(
    String format_version,
    @JsonProperty("minecraft:block") BlockBody_v1_16_100 minecraft_block
) {}

public record BlockBody_v1_16_100(
    BlockDescription_v1_16_100 description,
    BlockComponents_v1_16_100 components
) {}

public record BlockComponents_v1_16_100(
    // 1.16.100 特有字段
    @JsonProperty("minecraft:destroy_time") Optional<Float> minecraft_destroy_time,
    @JsonProperty("minecraft:light_emission") Optional<Integer> minecraft_light_emission,

    // 共同字段
    @JsonProperty("minecraft:collision_box") Optional<CollisionBox> minecraft_collision_box,
    @JsonProperty("minecraft:selection_box") Optional<SelectionBox> minecraft_selection_box
) {}

// ====== v1.19.20 版本 ======
public record BlockDef_v1_19_20(
    String format_version,
    @JsonProperty("minecraft:block") BlockBody_v1_19_20 minecraft_block
) {}

public record BlockBody_v1_19_20(
    BlockDescription_v1_19_20 description,
    BlockComponents_v1_19_20 components
) {}

public record BlockComponents_v1_19_20(
    // 已废弃字段（保留以支持旧版本解析）
    @JsonProperty("minecraft:destroy_time") Optional<Float> minecraft_destroy_time,

    // 新字段
    @JsonProperty("minecraft:destructible_by_mining")
    Optional<DestructibleByMining> minecraft_destructible_by_mining,

    // 共同字段
    @JsonProperty("minecraft:light_emission") Optional<Integer> minecraft_light_emission,
    @JsonProperty("minecraft:collision_box") Optional<CollisionBox> minecraft_collision_box
) {}

// ====== 最新版本（核心模型）======
/**
 * Block 定义（对应 format_version 1.21.60）
 * 核心业务逻辑只使用此版本，旧版本通过升级器自动转换
 */
public record BlockDef(
    String format_version,
    @JsonProperty("minecraft:block") BlockBody minecraft_block
) {}

public record BlockBody(
    BlockDescription description,
    BlockComponents components,
    @JsonProperty("permutations") Optional<List<BlockPermutation>> permutations
) {}

public record BlockComponents(
    // 只保留最新版本的字段（不包含历史废弃字段）
    @JsonProperty("minecraft:destructible_by_mining")
    Optional<DestructibleByMining> minecraft_destructible_by_mining,

    @JsonProperty("minecraft:block_light_emission")
    Optional<Float> minecraft_block_light_emission,  // 注意：1.20.60+ 使用 float

    @JsonProperty("minecraft:collision_box") Optional<CollisionBox> minecraft_collision_box,
    @JsonProperty("minecraft:selection_box") Optional<SelectionBox> minecraft_selection_box,
    @JsonProperty("minecraft:geometry") Optional<Geometry> minecraft_geometry
    // ... 其他最新字段
) {}
```

#### 4.2.3 共享类型的设计

**对于跨版本不变的类型，使用共享类**：

```java
/**
 * 碰撞盒定义（1.16.100 - 1.21.60 格式未变）
 */
public record CollisionBox(
    @JsonProperty("origin") Optional<float[]> origin,  // [x, y, z]
    @JsonProperty("size") Optional<float[]> size       // [width, height, depth]
) {}

/**
 * 硬度与挖掘定义（1.19.20 引入，格式稳定）
 */
public record DestructibleByMining(
    @JsonProperty("seconds_to_destroy") float seconds_to_destroy,
    @JsonProperty("item_specific_speeds") Optional<List<ItemSpecificSpeed>> item_specific_speeds
) {}

public record ItemSpecificSpeed(
    @JsonProperty("item") String item,
    @JsonProperty("destroy_speed") float destroy_speed
) {}
```

**对于有版本差异的类型，使用版本后缀**：

```java
// 1.16.100 - 1.19.80 版本的 Description
public record BlockDescription_v1_16_100(
    @JsonProperty("identifier") String identifier,
    @JsonProperty("states") Optional<BlockStates> states
) {}

// 1.20.0+ 版本的 Description（新增 traits 字段）
public record BlockDescription(
    @JsonProperty("identifier") String identifier,
    @JsonProperty("states") Optional<BlockStates> states,
    @JsonProperty("traits") Optional<BlockTraits> traits,  // 新增
    @JsonProperty("menu_category") Optional<MenuCategory> menu_category
) {}
```

#### 4.2.4 使用 Builder 简化升级器

```java
// 推荐使用 Lombok @Builder 或 Java 16+ Record Builder
@Builder
public record BlockComponents_v1_19_20(
    Optional<Float> minecraft_destroy_time,
    Optional<DestructibleByMining> minecraft_destructible_by_mining,
    Optional<Integer> minecraft_light_emission,
    Optional<CollisionBox> minecraft_collision_box
) {
    /**
     * 从旧版本创建 Builder（自动复制共同字段）
     */
    public static BlockComponents_v1_19_20Builder from(BlockComponents_v1_16_100 old) {
        return builder()
            // 自动复制所有共同字段
            .minecraft_light_emission(old.minecraft_light_emission())
            .minecraft_collision_box(old.minecraft_collision_box())
            .minecraft_selection_box(old.minecraft_selection_box());
            // 不复制已废弃字段（destroy_time），由升级器手动处理
    }
}

// 升级器中使用
public class BlockUpgrader_1_16_100_to_1_19_20 {
    public BlockDef_v1_19_20 upgrade(BlockDef_v1_16_100 from, UpgradeContext ctx) {
        var components = BlockComponents_v1_19_20.from(from.components())
            // 只需处理变更的字段
            .minecraft_destructible_by_mining(
                from.components().minecraft_destroy_time().map(time ->
                    new DestructibleByMining(time, Optional.empty())
                )
            )
            .build();

        return new BlockDef_v1_19_20(
            "1.19.20",
            new BlockBody_v1_19_20(from.description(), components)
        );
    }
}
```

---

## 5. 具体升级器实现示例

### 5.1 Block：光照字段升级（1.19.20 → 1.20.60）

**变更内容**：
- `minecraft:light_emission: int (0-15)` → `minecraft:block_light_emission: float (0.0-1.0)`

```java
@UpgraderMetadata(
    type = "block",
    from = "1.19.20",
    to = "1.20.60"
)
public class BlockUpgrader_1_19_20_to_1_20_60
    implements Upgrader<BlockDef_v1_19_20, BlockDef_v1_20_60> {

    @Override
    public BlockDef_v1_20_60 upgrade(BlockDef_v1_19_20 from, UpgradeContext ctx) {

        BlockComponents_v1_20_60 newComponents = upgradeComponents(
            from.components(), ctx
        );

        return new BlockDef_v1_20_60(
            "1.20.60",  // 更新版本号
            from.description(),  // description 未变
            newComponents
        );
    }

    private BlockComponents_v1_20_60 upgradeComponents(
        BlockComponents_v1_19_20 old, UpgradeContext ctx
    ) {
        var builder = BlockComponents_v1_20_60.builder();

        // 迁移共同字段
        builder.minecraft_collision_box(old.minecraft_collision_box());
        builder.minecraft_selection_box(old.minecraft_selection_box());

        // 光照字段迁移
        old.minecraft_light_emission().ifPresent(intValue -> {
            float floatValue = intValue / 15.0f;  // 转换：0-15 → 0.0-1.0
            builder.minecraft_block_light_emission(Optional.of(floatValue));
            ctx.addMigrationNote(
                "Converted light_emission from int to float: " + intValue + " → " + floatValue
            );
        });

        // 如果旧版本没有该字段，1.20.60 也保持为空
        if (old.minecraft_light_emission().isEmpty()) {
            builder.minecraft_block_light_emission(Optional.empty());
        }

        return builder.build();
    }
}
```

### 5.2 Block：硬度字段重构（1.16.100 → 1.19.20）

**变更内容**：
- `minecraft:destroy_time: float`
- → `minecraft:destructible_by_mining: { seconds_to_destroy: float, item_specific_speeds: [...] }`

```java
@UpgraderMetadata(type = "block", from = "1.16.100", to = "1.19.20")
public class BlockUpgrader_1_16_100_to_1_19_20
    implements Upgrader<BlockDef_v1_16_100, BlockDef_v1_19_20> {

    @Override
    public BlockDef_v1_19_20 upgrade(BlockDef_v1_16_100 from, UpgradeContext ctx) {

        var newComponents = BlockComponents_v1_19_20.builder();

        // 迁移 destroy_time → destructible_by_mining
        from.components().minecraft_destroy_time().ifPresent(destroyTime -> {
            DestructibleByMining destructible = new DestructibleByMining(
                destroyTime,           // seconds_to_destroy
                null                   // item_specific_speeds (旧版本没有)
            );
            newComponents.minecraft_destructible_by_mining(Optional.of(destructible));
            ctx.addMigrationNote(
                "Migrated destroy_time to destructible_by_mining: " + destroyTime
            );
        });

        // 迁移其他字段...

        return new BlockDef_v1_19_20(
            "1.19.20",
            from.description(),
            newComponents.build()
        );
    }
}
```

### 5.3 Entity：组件默认值填充（1.16.0 → 1.20.0）

**变更内容**：
- 新增 `minecraft:pushable` 组件，默认值为 `{ is_pushable: true }`

```java
@UpgraderMetadata(type = "entity", from = "1.16.0", to = "1.20.0")
public class EntityUpgrader_1_16_0_to_1_20_0
    implements Upgrader<EntityDef_v1_16_0, EntityDef_v1_20_0> {

    @Override
    public EntityDef_v1_20_0 upgrade(EntityDef_v1_16_0 from, UpgradeContext ctx) {

        var newComponents = EntityComponents_v1_20_0.builder();

        // 复制旧组件
        newComponents.minecraft_physics(from.components().minecraft_physics());
        newComponents.minecraft_scale(from.components().minecraft_scale());

        // 填充新组件的默认值
        if (from.components().minecraft_pushable().isEmpty()) {
            newComponents.minecraft_pushable(
                Optional.of(new ComponentPushable(true, true))
            );
            ctx.addMigrationNote("Added default minecraft:pushable component");
        }

        return new EntityDef_v1_20_0(
            "1.20.0",
            from.description(),
            newComponents.build()
        );
    }
}
```

---

## 6. 优化策略

### 6.1 性能优化

**问题**：每个 JSON 文件都需要经过升级链，可能影响解析速度

**方案 1：版本 DTO 缓存**
```java
// 缓存已解析过的版本 DTO，避免重复升级
private static final LoadingCache<CacheKey, BlockDef> CACHE = Caffeine.newBuilder()
    .maximumSize(1000)
    .build(key -> {
        // 执行完整的解析+升级流程
    });
```

**方案 2：短路优化**
```java
// 如果输入已是最新版本，跳过升级链
if (version.equals(KnownVersions.BLOCK_LATEST)) {
    return Mappers.MAPPER.treeToValue(root, BlockDef.class);
}
```

**方案 3：懒升级（Lazy Upgrade）**
```java
// 只在实际访问字段时才执行升级
public class LazyBlockDef {
    private final JsonNode rawData;
    private final FormatVersion version;
    private BlockDef upgraded;  // 延迟初始化

    public BlockComponents components() {
        if (upgraded == null) {
            upgraded = performUpgrade();
        }
        return upgraded.components();
    }
}
```

### 6.2 架构选择：强类型 DTO vs JsonNode

**结论：优先使用强类型 DTO**

**理由**：
1. **内存不是问题**：升级过程是短暂的，中间版本对象在升级完成后立即被 GC 回收
2. **类型安全**：编译期检查，避免字段名拼写错误
3. **可维护性**：IDE 支持重构、代码导航、自动补全
4. **可测试性**：强类型对象更容易编写单元测试

**推荐方案**：为每个重要版本创建独立的 DTO

```java
// 每个版本都是独立的 record 类
public record BlockDef_v1_16_100(
    String format_version,
    BlockDescription_v1_16_100 description,
    BlockComponents_v1_16_100 components
) {}

public record BlockDef_v1_19_20(
    String format_version,
    BlockDescription_v1_19_20 description,
    BlockComponents_v1_19_20 components
) {}

// 升级器使用强类型
public class BlockUpgrader_1_16_100_to_1_19_20
    implements Upgrader<BlockDef_v1_16_100, BlockDef_v1_19_20> {

    @Override
    public BlockDef_v1_19_20 upgrade(BlockDef_v1_16_100 from, UpgradeContext ctx) {
        // 类型安全的字段访问
        float destroyTime = from.components().minecraft_destroy_time().orElse(0f);

        // 清晰的数据转换逻辑
        var destructible = new DestructibleByMining(destroyTime, null);

        return new BlockDef_v1_19_20(
            "1.19.20",
            from.description(),
            BlockComponents_v1_19_20.builder()
                .minecraft_destructible_by_mining(Optional.of(destructible))
                .build()
        );
    }
}
```

**性能分析**：
```
加载 100 个 Block JSON (1.16.100 → 1.21.60)
├─ 解析阶段：创建 100 个 BlockDef_v1_16_100 对象
├─ 升级阶段：创建 100 个中间对象 (v1_19_20, v1_20_60)
└─ 完成后：只保留 100 个 BlockDef_v1_21_60 对象

内存峰值：约 400 个对象（4x 版本数量）
持续占用：100 个对象（最终版本）
GC 回收：300 个中间对象（毫秒级完成）
```

**何时考虑 JsonNode**（仅作为例外）：
- 处理**完全未知**的结构（如用户自定义字段）
- 需要**动态生成**升级逻辑（如通过配置文件定义升级规则）
- 性能实测证明确实存在瓶颈（极不可能）

---

## 7. 测试策略

### 7.1 单元测试

**测试每个升级器的正确性**：
```java
@Test
void testLightEmissionUpgrade() {
    // 准备 1.19.20 版本的数据
    BlockDef_v1_19_20 oldBlock = BlockDef_v1_19_20.builder()
        .format_version("1.19.20")
        .components(BlockComponents_v1_19_20.builder()
            .minecraft_light_emission(Optional.of(10))  // 整数
            .build())
        .build();

    // 执行升级
    BlockUpgrader_1_19_20_to_1_20_60 upgrader = new BlockUpgrader_1_19_20_to_1_20_60();
    UpgradeContext ctx = new UpgradeContext("1.19.20", "1.20.60");
    BlockDef_v1_20_60 newBlock = upgrader.upgrade(oldBlock, ctx);

    // 验证结果
    assertEquals(10 / 15.0f, newBlock.components().minecraft_block_light_emission().get(), 0.001);
    assertTrue(ctx.getMigrationNotes().contains("Converted light_emission"));
}
```

### 7.2 集成测试

**测试完整的升级链**：
```java
@Test
void testFullUpgradeChain() {
    // 加载 1.16.100 版本的真实 JSON
    String json = """
        {
          "format_version": "1.16.100",
          "minecraft:block": {
            "description": { "identifier": "custom:test_block" },
            "components": {
              "minecraft:destroy_time": 2.5,
              "minecraft:light_emission": 12
            }
          }
        }
        """;

    // 解析（自动升级到最新版本）
    BlockDef block = Mappers.MAPPER.readValue(json, BlockDef.class);

    // 验证升级后的字段
    assertEquals("1.21.60", block.format_version());  // 版本已更新
    assertEquals(2.5f, block.components().minecraft_destructible_by_mining().get().seconds_to_destroy());
    assertEquals(12 / 15.0f, block.components().minecraft_block_light_emission().get(), 0.001);
}
```

### 7.3 兼容性测试

**使用真实的 Addon 样例**：
1. 从 bedrock-dot-dev 文档或社区收集不同版本的真实 JSON
2. 创建测试集：`test-data/blocks/v1.16.100/*.json`、`test-data/blocks/v1.19.20/*.json`
3. 批量测试所有文件都能正确升级且无异常

```java
@ParameterizedTest
@MethodSource("loadAllBlockJsons")
void testRealWorldBlocks(Path jsonFile) {
    BlockDef block = Mappers.MAPPER.readValue(jsonFile.toFile(), BlockDef.class);
    assertNotNull(block);
    assertEquals(KnownVersions.BLOCK_LATEST.toVersionString(), block.format_version());
}
```

---

## 8. 实施路线图

### Phase 1：基础设施搭建（1-2 周）

- [x] 定义 `FormatVersion` 类和版本常量
- [ ] 实现 `Upgrader` 接口和 `UpgradeContext`
- [ ] 实现 `UpgraderRegistry` 和升级链构建算法
- [ ] 集成到 Jackson：实现 `VersionAwareDeserializer`
- [ ] 编写基础单元测试

### Phase 2：Block 类型支持（2-3 周）

- [ ] 定义 Block 的主要版本 DTO（1.16.100, 1.19.20, 1.20.60, 1.21.60）
- [ ] 实现 Block 升级器：
  - [ ] `BlockUpgrader_1_16_100_to_1_19_20`（硬度字段重构）
  - [ ] `BlockUpgrader_1_19_20_to_1_20_60`（光照字段升级）
  - [ ] `BlockUpgrader_1_20_60_to_1_21_60`（最新特性）
- [ ] 从 reference/ECProEntity 迁移 Block 组件解析逻辑
- [ ] 使用真实 Block JSON 进行集成测试

### Phase 3：Entity 类型支持（2-3 周）

- [ ] 定义 Entity 的主要版本 DTO
- [ ] 实现 Entity 升级器链
- [ ] 迁移 Entity 组件解析逻辑
- [ ] 集成测试

### Phase 4：其他类型扩展（3-4 周）

- [ ] Item 类型支持
- [ ] Recipe 类型支持
- [ ] Loot Table 类型支持
- [ ] Biome 类型支持（如果需要）

### Phase 5：优化与完善（2 周）

- [ ] 性能优化（缓存、短路优化）
- [ ] 错误处理和日志完善
- [ ] 文档和使用示例
- [ ] 与现有 addon-bridge-core 集成

---

## 9. 版本信息来源与维护

### 9.1 参考文档

1. **bedrock-dot-dev**（`reference/bedrock-dot-dev`）
   - 提供各版本的组件文档和 schema
   - 需要手动查阅 Changelog 确定版本间差异

2. **Minecraft Wiki**
   - [Bedrock Edition version history](https://minecraft.wiki/w/Bedrock_Edition_version_history)
   - 包含每个版本的特性变更

3. **社区资源**
   - [bedrock-samples](https://github.com/Mojang/bedrock-samples)：官方示例
   - [bedrock-schemas](https://github.com/bedrock-dot-dev/schemas)：社区维护的 JSON Schema

### 9.2 版本升级器的维护

#### 9.2.1 新版本发布时的流程

**完整的版本升级工作流**：

```
┌─────────────────────────────────────────────────────────────────┐
│ 1. 监控 Minecraft 版本更新                                       │
│    - 订阅 Minecraft Changelog                                   │
│    - 关注 bedrock-dot-dev 仓库更新                               │
│    - 查看 bedrock-samples 新增文件                               │
└──────────────────────┬──────────────────────────────────────────┘
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│ 2. 识别变更（创建 CHANGELOG.md）                                │
│    - 对比新旧版本的 JSON Schema                                  │
│    - 列出新增/废弃/修改的字段                                     │
│    - 标注变更类型（重命名/类型变化/结构重组）                       │
└──────────────────────┬──────────────────────────────────────────┘
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│ 3. 创建版本 DTO                                                  │
│    - 在 versioned/v1_XX_XX/ 下创建新版本的 DTO                   │
│    - 更新 model/ 中的最新版本模型                                │
│    - 更新 KnownVersions 常量                                     │
└──────────────────────┬──────────────────────────────────────────┘
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│ 4. 实现升级器                                                    │
│    - 创建新的 Upgrader 实现                                      │
│    - 处理字段迁移逻辑                                            │
│    - 添加升级日志                                                │
└──────────────────────┬──────────────────────────────────────────┘
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│ 5. 测试与验证                                                    │
│    - 编写单元测试（覆盖所有字段变更）                              │
│    - 使用真实 JSON 进行集成测试                                  │
│    - 回归测试（确保旧版本升级链仍正常）                            │
└──────────────────────┬──────────────────────────────────────────┘
                       ▼
┌─────────────────────────────────────────────────────────────────┐
│ 6. 文档更新                                                      │
│    - 更新 docs/version-changelog/BLOCK_CHANGELOG.md             │
│    - 更新 README 中的支持版本信息                                │
│    - 更新 API 文档                                               │
└─────────────────────────────────────────────────────────────────┘
```

#### 9.2.2 版本变更追踪文档

**为每个类型维护 Changelog**：

**文件：`docs/version-changelog/BLOCK_CHANGELOG.md`**

```markdown
# Block 格式版本变更历史

## 1.21.60 → 1.22.0 (2025-XX)

### 新增字段
- `minecraft:waterlogged`: 支持方块被水淹没
  - 类型：`boolean | { can_waterlog: boolean }`
  - 默认值：`false`
  - 影响：新字段，旧版本填充默认值

### 修改字段
- `minecraft:geometry`
  - 旧格式：`string | { identifier: string }`
  - 新格式：`string | { identifier: string, culling: string, bone_visibility: {...} }`
  - 迁移：保留原有字段，新增字段填充默认值

### 废弃字段
- 无

### 升级器
- `BlockUpgrader_1_21_60_to_1_22_0`

---

## 1.20.60 → 1.21.60 (2024-12)

### 新增字段
- 无

### 修改字段
- `minecraft:collision_box` / `minecraft:selection_box`
  - 变更：服务端不再需要计算旋转后的盒子
  - 影响：升级器无需处理，客户端自动计算

### 废弃字段
- 无

### 升级器
- `BlockUpgrader_1_20_60_to_1_21_60`

---

## 1.19.20 → 1.20.60 (2024-01)

### 新增字段
- `minecraft:block_light_emission`: float (0.0 - 1.0)
  - 替代：`minecraft:light_emission` (int 0-15)

### 修改字段
- **光照系统重构**
  - `minecraft:light_emission` (int) → `minecraft:block_light_emission` (float)
  - 转换公式：`float_value = int_value / 15.0`

### 废弃字段
- `minecraft:light_emission` (int)
- `minecraft:light_dampening` (int)
- `minecraft:block_light_filter` (int)

### 升级器
- `BlockUpgrader_1_19_20_to_1_20_60`
- 关键逻辑：光照值从整数转浮点数

---

## 1.16.100 → 1.19.20 (2022-08)

### 新增字段
- `minecraft:destructible_by_mining`
  - 结构：`{ seconds_to_destroy: float, item_specific_speeds: [...] }`
  - 替代：`minecraft:destroy_time` (float)

### 修改字段
- 无

### 废弃字段
- `minecraft:destroy_time` (float)

### 升级器
- `BlockUpgrader_1_16_100_to_1_19_20`
- 关键逻辑：将 `destroy_time` 迁移到 `destructible_by_mining.seconds_to_destroy`
```

#### 9.2.3 自动化工具（未来可选）

**版本对比工具**（未来可实现）：

```bash
# 自动对比两个版本的 JSON Schema 差异
./gradlew compareVersions --from 1.21.60 --to 1.22.0 --type block

# 输出差异报告
# ====== Block: 1.21.60 → 1.22.0 ======
# [NEW] minecraft:waterlogged (boolean | object)
# [MOD] minecraft:geometry (structure expanded)
# [DEL] 无
```

#### 9.2.4 示例：添加 1.22.0 支持

**步骤 1：定义新版本常量**
```java
// KnownVersions.java
public static final FormatVersion BLOCK_V1_22_0 = new FormatVersion(1, 22, 0);
public static final FormatVersion BLOCK_LATEST = BLOCK_V1_22_0;  // ← 更新
```

**步骤 2：创建新版本 DTO**
```java
// versioned/v1_22_0/block/BlockComponents_v1_22_0.java
public record BlockComponents_v1_22_0(
    // 继承 1.21.60 的所有字段
    Optional<DestructibleByMining> minecraft_destructible_by_mining,
    Optional<Float> minecraft_block_light_emission,

    // 新增字段
    @JsonProperty("minecraft:waterlogged")
    Optional<Waterlogged> minecraft_waterlogged
) {}

public sealed interface Waterlogged {
    record Simple(boolean enabled) implements Waterlogged {}
    record Detailed(boolean can_waterlog, /* 其他字段 */) implements Waterlogged {}
}
```

**步骤 3：实现升级器**
```java
// upgraders/block/BlockUpgrader_1_21_60_to_1_22_0.java
@UpgraderMetadata(type = "block", from = "1.21.60", to = "1.22.0")
public class BlockUpgrader_1_21_60_to_1_22_0
    implements Upgrader<BlockDef_v1_21_60, BlockDef_v1_22_0> {

    @Override
    public BlockDef_v1_22_0 upgrade(BlockDef_v1_21_60 from, UpgradeContext ctx) {
        var components = BlockComponents_v1_22_0.builder()
            // 复制旧字段
            .from(from.components())
            // 新字段填充默认值
            .minecraft_waterlogged(Optional.empty())  // 默认不可被水淹没
            .build();

        ctx.addMigrationNote("Added waterlogged support (default: false)");

        return new BlockDef_v1_22_0("1.22.0", from.minecraft_block());
    }
}
```

**步骤 4：注册升级器**
```java
// upgraders/UpgraderRegistration.java
static {
    // ... 其他升级器
    registry.register("block", new BlockUpgrader_1_21_60_to_1_22_0());
}
```

**步骤 5：更新最新版本模型**
```java
// model/block/BlockComponents.java（核心模型）
public record BlockComponents(
    Optional<DestructibleByMining> minecraft_destructible_by_mining,
    Optional<Float> minecraft_block_light_emission,
    Optional<Waterlogged> minecraft_waterlogged  // ← 新增
) {}
```

**步骤 6：编写测试**
```java
@Test
void testWaterloggedUpgrade() {
    BlockDef_v1_21_60 oldBlock = /* 构造旧版本数据 */;

    BlockUpgrader_1_21_60_to_1_22_0 upgrader = new BlockUpgrader_1_21_60_to_1_22_0();
    BlockDef_v1_22_0 newBlock = upgrader.upgrade(oldBlock, new UpgradeContext());

    // 验证新字段默认值
    assertTrue(newBlock.components().minecraft_waterlogged().isEmpty());
}
```

---

## 10. 与现有系统集成

### 10.1 与 addon-bridge-core 集成

**当前的 AddonPack 模型**（来自 `bedrock-addons-jackson-plan.md`）：
```java
public record AddonPack(
    Manifest manifest,
    List<ItemDef> items,
    List<BlockDef> blocks,
    List<EntityDef> entities,
    List<RecipeDef> recipes,
    Map<String, byte[]> resourceFiles
) {}
```

**集成后的流程**：
```java
public final class AddonLoader {
    public AddonPack load(Path root) throws Exception {
        // 1. 解析 manifest（通常版本稳定，可能不需要升级）
        Manifest mf = Mappers.MAPPER.readValue(
            root.resolve("manifest.json").toFile(), Manifest.class
        );

        // 2. 解析 blocks（自动升级到最新版本）
        List<BlockDef> blocks = parseDir(root.resolve("blocks"), path ->
            Mappers.MAPPER.readValue(path.toFile(), BlockDef.class)
            // ↑ Jackson 内部会调用 BlockDefDeserializer，自动执行升级链
        );

        // 3. 解析 entities
        List<EntityDef> entities = parseDir(root.resolve("entities"), path ->
            Mappers.MAPPER.readValue(path.toFile(), EntityDef.class)
        );

        // 4. 其他类型...

        return new AddonPack(mf, null, blocks, entities, null, null);
    }
}
```

**关键点**：
- ✅ **对外透明**：`AddonLoader` 使用方无需关心版本升级
- ✅ **统一接口**：所有类型的 `Def` 都是最新版本
- ✅ **可追溯**：通过 `UpgradeContext` 可获取升级日志

### 10.2 与适配器（Adapter）集成

**当前的适配器接口**（来自 README）：
```java
public interface ServerRegistry {
    void registerBlock(BlockDef block);
    void registerEntity(EntityDef entity);
    // ...
}
```

**优势**：适配器只需处理最新版本的 `BlockDef`，无需考虑历史兼容性

**示例（PNX 适配器）**：
```java
public class PNXRegistry implements ServerRegistry {
    @Override
    public void registerBlock(BlockDef block) {
        // block 已经是 1.21.60 版本，可直接映射到 PNX API
        int runtimeId = Blocks.allocateCustomBlockId();

        // 提取组件（都是最新格式）
        float lightEmission = block.components()
            .minecraft_block_light_emission()
            .orElse(0.0f);

        Blocks.registerCustomBlock(
            block.description().identifier(),
            runtimeId,
            CustomBlock.class,
            // ...
        );
    }
}
```

---

## 11. 风险与挑战

| 风险 | 影响 | 缓解措施 |
|------|------|---------|
| **版本信息不完整** | 无法准确识别所有版本间的差异 | 1. 优先支持主流版本<br>2. 社区贡献升级器<br>3. 提供"宽松模式"（忽略未知字段） |
| **升级器错误** | 数据丢失或转换错误 | 1. 完善单元测试<br>2. 真实数据集成测试<br>3. 升级日志和回滚机制 |
| **性能开销** | 大量 JSON 解析时性能下降 | 1. 缓存机制<br>2. 短路优化<br>3. 并行解析 |
| **维护成本高** | 每次新版本需要更新升级器 | 1. 自动化测试<br>2. 版本变更文档<br>3. 社区参与 |

---

## 12. 备选方案

### 方案 A：仅支持最新版本（不推荐）

**做法**：强制要求所有 JSON 使用最新 `format_version`，拒绝旧版本

**优点**：
- 实现简单，无需升级系统
- 核心代码清晰

**缺点**：
- ❌ 用户体验差，需要手动更新所有 JSON
- ❌ 与 Bedrock 生态不兼容
- ❌ 无法使用社区现有的旧版本 Addon

### 方案 B：静态转换工具（可作为辅助）

**做法**：提供命令行工具，预先将旧版本 JSON 转换为新版本

```bash
addon-bridge-cli upgrade --input old-addon/ --output new-addon/ --target 1.21.60
```

**优点**：
- ✅ 运行时无性能开销
- ✅ 用户可预览和修改转换结果

**缺点**：
- 需要额外步骤
- 不支持动态加载

**结论**：可作为**运行时升级系统的补充**，提供离线转换功能

---

## 13. 总结

### 核心设计要点

1. **版本化 DTO**：为主要版本定义独立的数据类
2. **升级器链**：通过组合多个小升级器实现复杂的版本迁移
3. **Jackson 集成**：自定义反序列化器透明执行升级
4. **可扩展架构**：新版本发布时只需添加新的升级器

### 关键设计决策（已确认）

| 决策项 | 方案 | 理由 |
|--------|------|------|
| **版本支持范围** | 最早从 1.16.100 开始 | 首个稳定支持自定义方块/实体的版本，社区使用率高 |
| **Beta 版本支持** | ❌ 不支持 | Beta 版本格式不稳定，社区使用率低 |
| **字段命名规范** | ✅ 保留 JSON 原始命名（snake_case） | 可读性好、调试友好、工具支持好 |
| **DTO 设计策略** | ✅ 强类型 DTO（每版本独立） | 类型安全、可维护性高、GC 自动回收中间对象 |
| **内存优化** | ❌ 不使用 JsonNode | 转换是短暂的，内存不是问题，强类型优先 |
| **升级方向** | ✅ 仅支持向前升级（旧→新） | 核心只需最新版本，降级功能可选 |

### 架构优势

- ✅ **核心代码简化**：只需处理最新版本，无历史包袱
- ✅ **生态兼容性**：支持社区所有版本（1.16.100+）的 Addon
- ✅ **可维护性**：版本变更逻辑隔离在升级器中
- ✅ **用户友好**：无需手动更新旧版本 JSON，透明升级
- ✅ **类型安全**：强类型 DTO，编译期检查
- ✅ **易测试**：升级器独立单元测试，集成测试覆盖真实 JSON

### 与 ECProEntity 的改进

| ECProEntity（现状） | 新设计（本方案） | 改进效果 |
|-------------------|----------------|---------|
| 读取 `format_version` 但不使用 | 根据版本执行升级链 | ✅ 真正的版本兼容性 |
| `toNBT()` 同时输出多个版本字段 | 解析时统一升级到最新版本 | ✅ 核心代码清晰，无冗余字段 |
| 版本兼容逻辑分散在各处 | 升级器独立封装 | ✅ 易于维护和扩展 |
| 使用 Gson 解析 | 使用 Jackson + 自定义反序列化器 | ✅ 更强大的类型系统支持 |

### 预期收益

**开发者体验**：
- 核心代码只关注业务逻辑，无需考虑版本兼容性
- IDE 支持完善（自动补全、重构、导航）
- 测试简单（升级器独立测试）

**用户体验**：
- 支持所有主流版本的 Addon（1.16.100+）
- 无需手动更新旧版本 JSON
- 错误信息清晰（版本不支持、升级失败）

**可维护性**：
- 新版本发布时有清晰的添加流程
- 版本变更有完整的文档追踪
- 升级逻辑独立，不影响核心代码

### 实施建议

#### Phase 1：基础设施（优先级：高）
- [ ] 实现 `FormatVersion` 和 `KnownVersions`
- [ ] 实现 `Upgrader` 接口和 `UpgradeChain`
- [ ] 实现 `UpgraderRegistry`
- [ ] 集成到 Jackson（`VersionAwareDeserializer`）

#### Phase 2：Block Pilot（优先级：高）
**建议从这些组件开始**（影响最大、变更最典型）：
1. **光照字段**（int → float 类型变化）
2. **硬度字段**（字段重命名 + 结构重组）
3. **碰撞盒/选择盒**（跨版本稳定，作为共享类型）

**Pilot 验证目标**：
- ✅ 架构可行性
- ✅ 升级器编写流程是否顺畅
- ✅ 性能是否满足要求
- ✅ 测试策略是否完善

#### Phase 3-5：扩展到其他类型（优先级：中）
- Entity → Item → Recipe → Loot Table
- 根据 Pilot 经验优化流程

### 风险控制

| 风险 | 概率 | 影响 | 缓解措施 |
|------|------|------|---------|
| 版本信息不完整 | 中 | 中 | 优先支持主流版本，社区贡献，提供宽松模式 |
| 升级器实现错误 | 中 | 高 | 完善测试覆盖，真实 JSON 集成测试 |
| 性能问题 | 低 | 低 | 缓存、短路优化（如需要） |
| 维护成本高 | 中 | 中 | 清晰的文档、规范化流程、自动化工具 |

### 下一步行动

**立即开始**：
1. ✅ 创建 `docs/version-changelog/` 目录
2. ✅ 编写 `BLOCK_CHANGELOG.md`（根据 ECProEntity 和文档整理）
3. 🔜 创建 `addon-bridge-core` 模块结构
4. 🔜 实现 Phase 1 基础设施

**Pilot 阶段目标**（2-3 周）：
- 完整实现 Block 的 1.16.100 → 1.21.60 升级链
- 通过 3-5 个真实 Block JSON 测试
- 性能测试（100 个 Block 解析时间 < 500ms）
- 代码 Review 和架构调整

**后续扩展**：
- 基于 Pilot 经验优化升级器编写流程
- 扩展到 Entity、Item、Recipe 等其他类型
- 补充文档和使用示例

---

**文档版本**：v1.1
**作者**：EaseCation 团队
**最后更新**：2025-10-27
**状态**：设计方案（已讨论并确认关键决策）

**变更记录**：
- v1.1 (2025-10-27)：添加关键设计决策、模块结构、版本维护流程
- v1.0 (2025-10-27)：初始版本
