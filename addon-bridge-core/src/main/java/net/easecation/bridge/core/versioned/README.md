# 版本升级系统架构（Version Upgrade System）

## 设计理念

**应用层始终使用最新版本的 DTO，通过自动升级机制支持旧版本 behavior pack。**

### 核心原则

1. ✅ **单一版本原则**：应用代码只使用最新版本 DTO（如 `v1_21_60.Entity`）
2. ✅ **自动升级**：旧版本 behavior pack 自动升级到最新版本
3. ✅ **逐级迁移**：通过升级链逐步转换（1.19.0 → 1.19.40 → ... → 1.21.60）
4. ✅ **透明化**：对应用层完全透明，无需关心版本差异

### 为什么不使用接口抽象层？

❌ **接口层方案的问题**：
- 需要维护大量适配器类（每个版本一个）
- 接口无法覆盖所有版本特定字段
- 应用层丢失类型信息和 IDE 支持
- 扩展新组件需要同步修改接口

✅ **版本升级方案的优势**：
- 应用层始终使用最新版本，类型安全完整
- 只需维护升级规则，而非适配器
- 新版本只需添加增量升级步骤
- IDE 自动补全和类型检查

## 架构图

```
┌─────────────────────────────────────────────────────────────┐
│         Old Behavior Pack (format_version: 1.19.0)         │
│  {                                                          │
│    "format_version": "1.19.0",                              │
│    "minecraft:entity": { ... }                              │
│  }                                                          │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────────┐
│              EntityLoader (检测版本)                         │
│  - 读取 format_version: "1.19.0"                            │
│  - 加载 dto.entity.v1_19_0.Entity                           │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────────┐
│          VersionUpgrader (自动升级引擎)                      │
│                                                             │
│  v1_19_0.Entity                                             │
│       ↓ [UpgradeStep: v1.19.0 → v1.19.40]                  │
│  v1_19_40.Entity                                            │
│       ↓ [UpgradeStep: v1.19.40 → v1.19.80]                 │
│  v1_19_80.Entity                                            │
│       ↓ [UpgradeStep: v1.19.80 → v1.20.0]                  │
│  ...                                                        │
│       ↓ [UpgradeStep: v1.21.20 → v1.21.60]                 │
│  v1_21_60.Entity ✅ (最新版本)                              │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────────┐
│         EntityDef (始终使用最新版本)                         │
│  - components: v1_21_60.Components                          │
│  - description: v1_21_60.Description                        │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────────┐
│         EntityDataDriven (始终使用最新版本)                  │
│  - 直接访问 v1_21_60.Components 字段                        │
│  - 类型安全，IDE 自动补全                                    │
└─────────────────────────────────────────────────────────────┘
```

## 核心组件

### 1. FormatVersion（版本标识）

封装版本号，支持解析和比较。

```java
FormatVersion version = FormatVersion.parse("1.19.0");
boolean isOld = version.compareTo(FormatVersion.LATEST) < 0;
```

### 2. EntityLoader（DTO 加载器）

负责根据 `format_version` 加载对应版本的 DTO 类。

```java
// 自动检测版本并加载
EntityLoader.LoadedEntity loaded = EntityLoader.load(jsonContent);

// loaded.version: FormatVersion(1, 19, 0)
// loaded.entity: v1_19_0.Entity 实例
```

### 3. VersionUpgrader（核心升级引擎）

将任意版本的 Entity DTO 升级到最新版本。

```java
// 输入：旧版本 Entity
Object oldEntity = ...; // v1_19_0.Entity

// 输出：最新版本 Entity
v1_21_60.Entity latestEntity = VersionUpgrader.upgradeEntity(oldEntity, fromVersion);
```

#### 升级链（Upgrade Chain）

```
v1.19.0 → v1.19.40 → v1.19.80 → v1.20.0 → v1.20.41 → v1.20.81 → v1.21.20 → v1.21.60
   ↓          ↓          ↓          ↓          ↓          ↓          ↓          ↓
Step1      Step2      Step3      Step4      Step5      Step6      Step7     (最新)
```

每个升级步骤（UpgradeStep）处理：
- 新增字段（填充默认值）
- 字段重命名（映射旧名称到新名称）
- 类型转换（如 Integer → Double）
- 结构变化（如拆分/合并字段）

### 4. UpgradeStep（升级步骤接口）

```java
public interface UpgradeStep {
    /**
     * 从哪个版本升级。
     */
    FormatVersion fromVersion();

    /**
     * 升级到哪个版本。
     */
    FormatVersion toVersion();

    /**
     * 执行升级。
     * @param oldEntity 旧版本 Entity 对象
     * @return 新版本 Entity 对象
     */
    Object upgrade(Object oldEntity) throws Exception;
}
```

## 实现示例

### 示例 1：简单字段添加

假设 v1.20.0 新增了 `minecraft:custom_hit_test` 组件：

```java
public class UpgradeV1_19_80_to_V1_20_0 implements UpgradeStep {
    @Override
    public Object upgrade(Object oldEntity) throws Exception {
        var old = (v1_19_80.Entity) oldEntity;

        // 提取旧数据
        var oldDescription = old.description();
        var oldComponents = old.components();

        // 构建新版本 Components，添加默认值
        var newComponents = new v1_20_0.Components(
            oldComponents.minecraft_health(),        // 保留
            oldComponents.minecraft_movement(),      // 保留
            // ... 其他旧字段
            null  // minecraft_customHitTest - 新字段，默认 null
        );

        // 构建新版本 Entity
        return new v1_20_0.Entity(oldDescription, newComponents, ...);
    }
}
```

### 示例 2：字段重命名

假设 v1.21.0 将 `minecraft:scale` 重命名为 `minecraft:size_scale`：

```java
public class UpgradeV1_20_81_to_V1_21_0 implements UpgradeStep {
    @Override
    public Object upgrade(Object oldEntity) throws Exception {
        var old = (v1_20_81.Entity) oldEntity;

        // 读取旧字段
        var oldScale = old.components().minecraft_scale();

        // 映射到新字段
        var newSizeScale = oldScale != null
            ? new v1_21_0.SizeScale(oldScale.value())
            : null;

        var newComponents = new v1_21_0.Components(
            // ... 其他字段
            newSizeScale  // 新名称
        );

        return new v1_21_0.Entity(..., newComponents, ...);
    }
}
```

### 示例 3：类型转换

假设 v1.21.20 将 `health.max` 从 `Integer` 改为 `Double`：

```java
public class UpgradeV1_21_0_to_V1_21_20 implements UpgradeStep {
    @Override
    public Object upgrade(Object oldEntity) throws Exception {
        var old = (v1_21_0.Entity) oldEntity;
        var oldHealth = old.components().minecraft_health();

        // 类型转换
        v1_21_20.Health newHealth = null;
        if (oldHealth != null) {
            Double newMax = oldHealth.max() != null
                ? oldHealth.max().doubleValue()
                : null;

            newHealth = new v1_21_20.Health(
                newMax,  // Integer → Double
                oldHealth.value()
            );
        }

        var newComponents = new v1_21_20.Components(
            newHealth,
            // ... 其他字段
        );

        return new v1_21_20.Entity(..., newComponents, ...);
    }
}
```

## 使用流程

### 应用层代码（始终最新版本）

```java
// 1. 加载并自动升级
String json = Files.readString(entityJsonPath);
v1_21_60.Entity entity = EntityLoader.loadAndUpgrade(json);

// 2. 创建 EntityDef（使用最新版本）
EntityDef def = EntityDef.fromEntity(entity);

// 3. 使用最新版本 Components
v1_21_60.Components components = def.components();

// 4. 类型安全访问
if (components.minecraft_health() != null) {
    Integer maxHealth = components.minecraft_health().max();
    this.setMaxHealth(maxHealth);
}
```

### EntityDataDriven 中使用

```java
// 注册组件（始终最新版本）
public static void registerComponents(String id, v1_21_60.Components components) {
    ID_COMPONENTS_MAP.put(id, components);
}

// 初始化（类型安全）
private void initComponents() {
    if (components == null) return;

    // 直接访问最新版本字段
    if (components.minecraft_health() != null) {
        var health = components.minecraft_health();
        if (health.max() != null) {
            this.setMaxHealth(health.max());
        }
    }

    // IDE 自动补全和类型检查
    if (components.minecraft_movement() != null) {
        Double speed = extractRangeValue(components.minecraft_movement().value());
        if (speed != null) {
            this.movementSpeed = speed.floatValue();
        }
    }
}
```

## 扩展新版本

### 步骤 1：生成新版本 DTO

```bash
# 生成 v1.22.0 DTO
cd codegen
npm run generate -- --mc-version 1.22.0
```

### 步骤 2：创建升级步骤

```java
// UpgradeV1_21_60_to_V1_22_0.java
public class UpgradeV1_21_60_to_V1_22_0 implements UpgradeStep {
    @Override
    public FormatVersion fromVersion() {
        return FormatVersion.V1_21_60;
    }

    @Override
    public FormatVersion toVersion() {
        return FormatVersion.V1_22_0;
    }

    @Override
    public Object upgrade(Object oldEntity) throws Exception {
        var old = (v1_21_60.Entity) oldEntity;

        // 处理字段变化
        // - 新增字段：添加默认值
        // - 重命名字段：映射旧名称到新名称
        // - 类型变化：执行转换

        return new v1_22_0.Entity(...);
    }
}
```

### 步骤 3：注册升级步骤

```java
// VersionUpgrader.java
static {
    // ... 现有升级步骤
    registerStep(new UpgradeV1_21_60_to_V1_22_0());
}
```

### 步骤 4：更新 LATEST 常量

```java
// FormatVersion.java
public static final FormatVersion V1_22_0 = new FormatVersion(1, 22, 0);
public static final FormatVersion LATEST = V1_22_0;  // 更新为最新
```

### 步骤 5：更新应用层代码

```java
// EntityDef.java - 更新类型引用
import net.easecation.bridge.core.dto.entity.v1_22_0.Entity;
import net.easecation.bridge.core.dto.entity.v1_22_0.Components;

public record EntityDef(
    String id,
    @Nullable Entity.Description description,
    @Nullable Components components,
    // ...
) {}
```

## 优势总结

1. **简化维护**：只需维护升级规则，无需适配器
2. **类型安全**：应用层使用完整类型，无丢失信息
3. **IDE 友好**：自动补全、类型检查、重构支持
4. **增量扩展**：新版本只需添加一个升级步骤
5. **透明化**：应用层无感知版本差异

## 与接口方案对比

| 特性 | 接口抽象层方案 ❌ | 版本升级方案 ✅ |
|------|-----------------|---------------|
| 应用层复杂度 | 需要使用接口，丢失类型信息 | 直接使用最新版本，类型安全 |
| 维护成本 | 每个版本需要适配器类 | 只需升级步骤（增量） |
| IDE 支持 | 仅接口方法，无版本特定字段 | 完整类型，自动补全 |
| 扩展性 | 接口变更影响所有适配器 | 只需添加增量升级步骤 |
| 性能 | 运行时接口调用 | 一次性升级，后续直接访问 |

## 下一步

- [ ] 实现 VersionUpgrader 核心引擎
- [ ] 实现具体升级步骤（v1.19.0 → v1.21.60）
- [ ] 集成到 EntityLoader
- [ ] 为 Block 和 Item 创建相同机制
