# 版本升级系统路线图

## 当前状态（已完成）

### ✅ 架构设计

已完成版本升级系统的完整架构设计，采用**应用层始终使用最新版本**的策略：

- **核心理念**：应用代码只使用最新版本 DTO（v1_21_60），旧版本通过自动升级转换
- **关键组件**：
  - `FormatVersion` - 版本号封装（已实现）
  - `VersionUpgrader` - 核心升级引擎（待实现）
  - `UpgradeStep` - 升级步骤接口（待实现）
  - `EntityLoader` - 加载并自动升级（待实现）

详细设计见：[addon-bridge-core/src/main/java/net/easecation/bridge/core/versioned/README.md](../addon-bridge-core/src/main/java/net/easecation/bridge/core/versioned/README.md)

### ✅ 应用层代码已统一使用最新版本

所有应用层代码已配置为使用 **v1_21_60** DTO：

1. **EntityDef** (`addon-bridge-core/src/main/java/net/easecation/bridge/core/EntityDef.java`)
   - 使用 `net.easecation.bridge.core.dto.entity.v1_21_60.Entity`
   - 使用 `v1_21_60.Components`
   - 类型安全，完整的 IDE 支持

2. **EntityDataDriven** (`adapter-easecation/.../entity/EntityDataDriven.java`)
   - 使用 `v1_21_60.Components`
   - 直接访问最新版本字段（`minecraft_health()` 等）
   - 完整的类型检查和自动补全

3. **BlockDef** / **ItemDef** 同样使用最新版本

### ✅ 多版本 DTO 生成

Codegen 系统已支持生成多个版本的 DTO：

```bash
# 已生成的版本
- v1_19_0   (8 个版本)
- v1_19_40
- v1_19_80
- v1_20_0
- v1_20_41
- v1_20_81
- v1_21_20
- v1_21_60  ← 当前最新
```

每个版本完全独立：
- `dto/entity/v1_19_0/Entity.java`
- `dto/entity/v1_21_60/Entity.java`
- ...

## 未来工作（待实现）

### 🚧 阶段 1：实现升级引擎核心

**目标**：创建 VersionUpgrader 核心，支持逐级升级。

#### 1.1 创建 UpgradeStep 接口

```java
// addon-bridge-core/.../versioned/upgrade/UpgradeStep.java
public interface UpgradeStep {
    FormatVersion fromVersion();
    FormatVersion toVersion();
    Object upgrade(Object oldEntity) throws Exception;
}
```

#### 1.2 实现 VersionUpgrader 引擎

```java
// addon-bridge-core/.../versioned/upgrade/VersionUpgrader.java
public class VersionUpgrader {
    private static final Map<FormatVersion, UpgradeStep> UPGRADE_CHAIN = new LinkedHashMap<>();

    static {
        // 注册升级步骤（按版本顺序）
        registerStep(new UpgradeV1_19_0_to_V1_19_40());
        registerStep(new UpgradeV1_19_40_to_V1_19_80());
        // ... 其他步骤
    }

    public static v1_21_60.Entity upgradeEntity(Object oldEntity, FormatVersion fromVersion) {
        // 逐级升级到最新版本
    }
}
```

**预计工作量**：2-3 小时

---

### 🚧 阶段 2：实现具体升级步骤

**目标**：为每个版本对实现升级逻辑。

#### 需要实现的升级步骤

1. `UpgradeV1_19_0_to_V1_19_40`
2. `UpgradeV1_19_40_to_V1_19_80`
3. `UpgradeV1_19_80_to_V1_20_0`
4. `UpgradeV1_20_0_to_V1_20_41`
5. `UpgradeV1_20_41_to_V1_20_81`
6. `UpgradeV1_20_81_to_V1_21_20`
7. `UpgradeV1_21_20_to_V1_21_60`

#### 实现策略

**初期**：使用通用升级器（Generic Upgrader）
- 通过反射自动复制所有同名字段
- 新增字段填充 `null`
- 适用于大多数向后兼容的版本更新

```java
public class GenericUpgradeStep implements UpgradeStep {
    @Override
    public Object upgrade(Object oldEntity) throws Exception {
        // 1. 提取旧版本字段值（通过反射）
        // 2. 构造新版本对象，复制同名字段
        // 3. 新字段使用 null 默认值
        return newEntity;
    }
}
```

**后期**：为有结构变化的版本创建专用升级器
- 字段重命名
- 类型转换
- 结构拆分/合并

**预计工作量**：
- 通用升级器：4-6 小时
- 专用升级器（按需）：每个 1-2 小时

---

### 🚧 阶段 3：集成到加载流程

**目标**：让 EntityDef/BlockDef 自动检测版本并升级。

#### 3.1 修改 EntityDef 加载方法

```java
// EntityDef.java
public static EntityDef fromJSON(String json) throws IOException {
    // 1. 检测 format_version
    FormatVersion version = VersionDetector.detectFromJson(json);

    // 2. 加载对应版本的 DTO
    Object oldEntity = EntityLoader.loadEntityDTO(json, version);

    // 3. 自动升级到最新版本
    v1_21_60.Entity latestEntity = VersionUpgrader.upgradeEntity(oldEntity, version);

    // 4. 创建 EntityDef（使用最新版本）
    return EntityDef.fromDTO(latestEntity);
}
```

#### 3.2 同样处理 BlockDef / ItemDef

**预计工作量**：2-3 小时

---

### 🚧 阶段 4：测试与验证

**目标**：确保升级正确性和兼容性。

#### 4.1 单元测试

为每个升级步骤创建单元测试：

```java
@Test
public void testUpgradeV1_19_0_to_V1_19_40() {
    // 加载 v1.19.0 测试数据
    v1_19_0.Entity oldEntity = loadTestEntity("test_entity_v1_19_0.json");

    // 升级
    v1_19_40.Entity newEntity = (v1_19_40.Entity) upgradeStep.upgrade(oldEntity);

    // 验证字段正确转换
    assertNotNull(newEntity.components());
    assertEquals(oldEntity.description().identifier(), newEntity.description().identifier());
}
```

#### 4.2 集成测试

加载真实的旧版本 behavior pack：

```java
@Test
public void testLoadOldBehaviorPack() {
    // 加载 1.19.0 behavior pack
    String json = loadResource("entities/zombie_v1.19.0.json");

    // 应该自动升级到 v1.21.60
    EntityDef def = EntityDef.fromJSON(json);

    // 验证升级正确
    assertNotNull(def.components());
    assertTrue(def.components() instanceof v1_21_60.Components);
}
```

**预计工作量**：4-6 小时

---

## 实现优先级

### P0 - 高优先级（必须）

1. ✅ FormatVersion 类（已完成）
2. ✅ 架构设计文档（已完成）
3. ✅ 应用层改为最新版本（已完成）
4. 🚧 UpgradeStep 接口
5. 🚧 VersionUpgrader 核心引擎
6. 🚧 GenericUpgradeStep 通用升级器

### P1 - 中优先级（推荐）

7. 🚧 EntityLoader 集成
8. 🚧 EntityDef.fromJSON() 自动升级
9. 🚧 单元测试套件

### P2 - 低优先级（优化）

10. 专用升级器（仅在需要时）
11. 升级性能优化
12. 升级日志和调试工具

## 工作量估算

| 阶段 | 预计时间 | 说明 |
|------|---------|------|
| ✅ 阶段 0：架构设计 | 4 小时 | 已完成 |
| 🚧 阶段 1：核心引擎 | 2-3 小时 | UpgradeStep + VersionUpgrader |
| 🚧 阶段 2：通用升级器 | 4-6 小时 | GenericUpgradeStep 实现 |
| 🚧 阶段 3：加载流程集成 | 2-3 小时 | EntityDef/BlockDef |
| 🚧 阶段 4：测试验证 | 4-6 小时 | 单元测试 + 集成测试 |
| **总计** | **16-22 小时** | 约 2-3 个工作日 |

## 示例代码片段

### 通用升级器示例

```java
public class GenericUpgradeStep implements UpgradeStep {
    private final Class<?> fromClass;
    private final Class<?> toClass;
    private final FormatVersion fromVersion;
    private final FormatVersion toVersion;

    @Override
    public Object upgrade(Object oldEntity) throws Exception {
        // 1. 获取旧版本所有字段
        Map<String, Object> oldFields = extractFields(oldEntity);

        // 2. 创建新版本实例（使用反射）
        Constructor<?> constructor = findCompatibleConstructor(toClass);

        // 3. 准备构造参数（匹配字段名）
        Object[] args = prepareConstructorArgs(constructor, oldFields);

        // 4. 创建新实例
        return constructor.newInstance(args);
    }

    private Map<String, Object> extractFields(Object entity) {
        // 使用反射提取所有 record 字段
        // description() -> oldFields.put("description", value)
        // components() -> oldFields.put("components", value)
    }

    private Object[] prepareConstructorArgs(Constructor<?> ctor, Map<String, Object> fields) {
        // 根据构造函数参数名称匹配字段
        // 新字段填充 null
    }
}
```

### 使用示例

```java
// 用户代码（完全透明）
String json = Files.readString(Paths.get("entities/custom_mob.json"));

// 自动检测版本并升级到最新
EntityDef def = EntityDef.fromJSON(json);

// 始终使用最新版本 API
if (def.components().minecraft_health() != null) {
    int health = def.components().minecraft_health().max();
}
```

## 后续扩展

### 添加新版本（如 v1.22.0）

1. 生成新版本 DTO：
   ```bash
   npm run generate -- --mc-version 1.22.0
   ```

2. 创建升级步骤：
   ```java
   public class UpgradeV1_21_60_to_V1_22_0 extends GenericUpgradeStep {
       // 大多数情况下无需额外代码（通用升级器自动处理）

       // 如果有特殊逻辑，可以覆盖：
       @Override
       public Object upgrade(Object oldEntity) throws Exception {
           // 调用通用升级
           var newEntity = super.upgrade(oldEntity);

           // 处理特殊字段
           // ...

           return newEntity;
       }
   }
   ```

3. 注册到升级链：
   ```java
   // VersionUpgrader.java
   registerStep(new UpgradeV1_21_60_to_V1_22_0());
   ```

4. 更新 `FormatVersion.LATEST` 常量

5. 更新应用层类型引用（仅一次，全局替换）

## 参考

- [多版本架构设计](../addon-bridge-core/src/main/java/net/easecation/bridge/core/versioned/README.md)
- [Codegen 文档](../codegen/README.md)
