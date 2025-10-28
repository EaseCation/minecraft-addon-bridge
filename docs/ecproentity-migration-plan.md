# ECProEntity 功能迁移实施计划

**版本**: 1.0
**日期**: 2025-10-28
**状态**: 进行中
**预计完成时间**: 6周

## 📋 目录

- [概述](#概述)
- [对比分析](#对比分析)
- [阶段一：方块NBT和组件系统](#阶段一方块nbt和组件系统)
- [阶段二：实体核心功能](#阶段二实体核心功能)
- [阶段三：资源包和生物群系](#阶段三资源包和生物群系)
- [阶段四：测试和优化](#阶段四测试和优化)
- [技术参考](#技术参考)
- [进度跟踪](#进度跟踪)

---

## 概述

### 项目目标

将 ECProEntity 插件的自定义方块和实体注册功能迁移到 nukkit-addon-bridge 框架中，实现：
1. 完整的方块组件支持（35个组件）
2. 完整的实体组件支持（14个组件）
3. 资源包自动发送
4. 生物群系支持
5. 保持架构优势（模块化、跨平台、类型安全）

### 架构优势

**当前实现相比ECProEntity的优势**：
- ✅ 模块化设计（core独立于adapter）
- ✅ 跨平台支持（PowerNukkit、CloudburstMC、MotMot等）
- ✅ 类型安全（sealed interface vs Either）
- ✅ 完整的DTO（自动生成100+字段 vs 手写14字段）
- ✅ 两阶段处理（解析与注册分离）

**需要补足的功能**：
- ❌ 方块组件实现（仅7/35）
- ❌ 实体组件实现（仅5/14）
- ❌ NBT序列化逻辑
- ❌ Traits/States/Permutations支持
- ❌ 资源包管理
- ❌ 高级实体功能（骑乘、伤害处理等）

---

## 对比分析

### 功能完整度对比

| 模块 | ECProEntity | 当前实现 | 差距 |
|------|-------------|----------|------|
| 方块组件 | 35个 | 7个 | 80% |
| 实体组件 | 14个 | 5个 | 64% |
| NBT序列化 | 完整 | 基础 | 70% |
| Traits/States | 完整 | 未实现 | 100% |
| Permutations | 完整 | 未实现 | 100% |
| 资源包 | 完整 | 未实现 | 100% |
| 生物群系 | 完整 | 未实现 | 100% |
| **总体** | **100%** | **~15%** | **85%** |

### 关键文件对应关系

| ECProEntity | 当前项目 | 状态 |
|-------------|----------|------|
| `BedrockAddonLoader.java` | `AddonParser.java` + `EasecationRegistry.java` | 部分完成 |
| `BlockComponents.java` | `BlockComponentsNBT.java` | ✅ 已创建 |
| `BlockDefinition.java` | `BlockDataDriven.BlockDefinition` | 简化版 |
| `BlockTraits.java` | 待创建 | ❌ |
| `BlockStates.java` | 待创建 | ❌ |
| `BlockPermutation.java` | 待创建 | ❌ |
| `EntityComponents.java` | DTO `Components.java` | DTO版本 |
| `BiomeDataDriven.java` | 待创建 | ❌ |

---

## 阶段一：方块NBT和组件系统

**目标**: 实现完整的方块注册和NBT序列化
**时间**: 第1-2周
**优先级**: 🔴 高

### 1.1 BlockComponentsNBT 类 ✅

**状态**: 已完成
**文件**: `adapter-easecation/src/.../block/BlockComponentsNBT.java`

**已实现的组件** (17/35):
- ✅ minecraft:light_emission (多协议版本)
- ✅ minecraft:light_dampening (多协议版本)
- ✅ minecraft:collision_box (完整)
- ✅ minecraft:selection_box (完整)
- ✅ minecraft:geometry (基础)
- ✅ minecraft:display_name
- ✅ minecraft:destructible_by_mining
- ✅ minecraft:destructible_by_explosion
- ✅ minecraft:flammable
- ✅ minecraft:friction
- ✅ minecraft:map_color
- ✅ minecraft:transformation
- ✅ minecraft:redstone_conductivity
- ✅ minecraft:destruction_particles
- ✅ minecraft:replaceable
- 🚧 minecraft:material_instances (框架)
- 🚧 minecraft:placement_filter (框架)
- 🚧 minecraft:liquid_detection (框架)
- 🚧 minecraft:item_visual (框架)

**待补全的组件** (16个):
- ❌ minecraft:unit_cube
- ❌ minecraft:breathability
- ❌ minecraft:movable
- ❌ minecraft:redstone_producer
- ❌ minecraft:flower_pottable
- ❌ minecraft:embedded_visual
- ❌ minecraft:random_offset
- ❌ minecraft:precipitation_interactions
- ❌ minecraft:support
- ❌ minecraft:connection_rule
- ❌ minecraft:crafting_table
- ❌ minecraft:tick
- ❌ minecraft:entity_fall_on
- ❌ minecraft:custom_components
- ❌ netease:render_layer
- ❌ netease:no_crop_face_block

**参考**: `reference/ECProEntity/.../BlockComponents.java:93-304`

### 1.2 BlockTraits 类

**状态**: 待实现
**文件**: `adapter-easecation/src/.../block/BlockTraits.java` (新建)

**需要实现的特性**:

#### 1.2.1 Placement Direction (放置方向)
```java
public class PlacementDirection {
    // Cardinal Direction: north, south, east, west
    // Facing Direction: down, up, north, south, east, west

    public static CompoundTag toNBT(PlacementDirection trait) {
        // 生成 placement_direction NBT
        // enabled_states: ["minecraft:cardinal_direction"] 或 ["minecraft:facing_direction"]
        // y_rotation_offset: 0
    }
}
```
**参考**: `reference/ECProEntity/.../BlockTraits.java`

#### 1.2.2 Placement Position (放置位置)
```java
public class PlacementPosition {
    // Block Face: down, up, north, south, east, west
    // Vertical Half: bottom, top

    public static CompoundTag toNBT(PlacementPosition trait) {
        // 生成 placement_position NBT
        // enabled_states: ["minecraft:block_face"] 或 ["minecraft:vertical_half"]
    }
}
```

#### 1.2.3 Connection (连接规则)
```java
public class Connection {
    // Cardinal Connections: north, south, east, west (boolean)

    public static CompoundTag toNBT(Connection trait) {
        // 生成连接规则NBT
        // 用于栅栏、墙壁等方块
    }
}
```

**集成到 buildBlockNBT()**:
```java
// 在 EasecationRegistry.buildBlockNBT() 中
if (blockDef.description() != null && blockDef.description().traits() != null) {
    CompoundTag traitsNBT = BlockTraits.toNBT(blockDef.description().traits());
    nbt.putCompound("traits", traitsNBT);
}
```

### 1.3 BlockStates 类

**状态**: 待实现
**文件**: `adapter-easecation/src/.../block/BlockStates.java` (新建)

**需要实现的状态类型**:

#### 1.3.1 Boolean States
```java
public class BooleanState {
    String name;
    boolean defaultValue;

    public CompoundTag toPropertyNBT() {
        // 转换为 NBT property
        // type: 1 (boolean)
        // enum: [0, 1]
    }
}
```

#### 1.3.2 Integer States
```java
public class IntegerState {
    String name;
    int min;
    int max;
    int defaultValue;

    public CompoundTag toPropertyNBT() {
        // type: 0 (int)
        // enum: [min, min+1, ..., max]
    }
}
```

#### 1.3.3 String States (Enum)
```java
public class StringState {
    String name;
    List<String> values;
    String defaultValue;

    public CompoundTag toPropertyNBT() {
        // type: 2 (string)
        // enum: ["value1", "value2", ...]
    }
}
```

**集成到 buildBlockNBT()**:
```java
if (blockDef.description() != null && blockDef.description().states() != null) {
    ListTag<CompoundTag> properties = BlockStates.toPropertiesNBT(blockDef.description().states());
    nbt.putList("properties", properties);
}
```

**参考**: `reference/ECProEntity/.../BlockStates.java`

### 1.4 BlockPermutation 类

**状态**: 待实现
**文件**: `adapter-easecation/src/.../block/BlockPermutation.java` (新建)

**核心功能**: 根据条件动态改变方块组件

#### 1.4.1 Condition 解析
```java
public class PermutationCondition {
    String molangExpression; // e.g., "q.block_state('my:state') == 1"

    public boolean evaluate(Map<String, Object> blockStates) {
        // Molang 表达式求值
        // 支持简单的比较运算符
    }
}
```

#### 1.4.2 Component Override
```java
public class Permutation {
    PermutationCondition condition;
    Component components; // 覆盖的组件

    public CompoundTag toNBT() {
        // 生成 permutation NBT
        // condition: molang 表达式
        // components: 组件 NBT
    }
}
```

**集成到 buildBlockNBT()**:
```java
if (blockDef.permutations() != null && !blockDef.permutations().isEmpty()) {
    ListTag<CompoundTag> permutations = new ListTag<>();
    for (Object permutation : blockDef.permutations()) {
        permutations.add(BlockPermutation.toNBT(permutation));
    }
    nbt.putList("permutations", permutations);
}
```

**参考**: `reference/ECProEntity/.../BlockPermutation.java`

### 1.5 扩展 BlockDefinition

**状态**: 待实现
**文件**: `adapter-easecation/.../BlockDataDriven.java`

**当前字段** (8个):
```java
public static class BlockDefinition {
    String identifier;
    SimpleAxisAlignedBB collisionBox;
    float hardness;
    float resistance;
    int lightEmission;
    int lightDampening;
    float friction;
    String displayName;
}
```

**需要添加的字段** (40+):

#### 1.5.1 选择箱和几何
```java
SimpleAxisAlignedBB selectionBox;
String geometryIdentifier;
Map<String, Boolean> boneVisibility;
```

#### 1.5.2 材质和视觉
```java
Map<String, MaterialInstance> materialInstances;
String mapColor;
String renderLayer; // netease:render_layer
```

#### 1.5.3 破坏和耐久
```java
Map<String, Float> itemSpecificSpeeds; // 特定工具的破坏速度
boolean flammable;
int catchChanceModifier;
int destroyChanceModifier;
```

#### 1.5.4 红石和交互
```java
boolean redstoneConductor;
boolean allowsWireToStepDown;
int redstonePower;
List<Direction> redstoneConnectedFaces;
```

#### 1.5.5 放置和移动
```java
List<String> allowedFaces; // placement_filter
List<String> allowedBlocks; // placement_filter
boolean movable; // 是否可被活塞推动
boolean noPistonPush;
```

#### 1.5.6 液体检测
```java
List<LiquidDetectionRule> liquidDetectionRules;
```

#### 1.5.7 其他
```java
boolean breathable; // 是否可呼吸
boolean unitCube; // 是否为单位立方体
boolean replaceable; // 是否可替换
float randomOffsetX, randomOffsetY, randomOffsetZ; // 随机偏移
String supportShape; // 支撑形状
List<CardinalDirection> connectionDirections; // 连接方向
```

**参考**: `reference/ECProEntity/.../BlockDefinition.java:17-70`

### 1.6 完善 BlockComponentsNBT 的 TODO

**状态**: 待实现

#### 1.6.1 Material Instances
```java
private static CompoundTag convertMaterialInstances(MaterialInstances instances) {
    CompoundTag result = new CompoundTag();

    // instances.materials: Map<String, MaterialInstance>
    for (String face : instances.materials().keySet()) {
        CompoundTag material = new CompoundTag();
        MaterialInstance instance = instances.materials().get(face);

        if (instance.texture() != null) {
            material.putString("texture", instance.texture());
        }
        if (instance.renderMethod() != null) {
            material.putString("render_method", instance.renderMethod());
        }

        result.putCompound(face, material);
    }

    return result;
}
```

**参考**: `reference/ECProEntity/.../ComponentMaterialInstances.java`

#### 1.6.2 Placement Filter
```java
private static CompoundTag convertPlacementFilter(PlacementFilter filter) {
    CompoundTag result = new CompoundTag();
    ListTag<CompoundTag> conditions = new ListTag<>();

    // allowed_faces: List<BlockFace>
    if (filter.allowedFaces() != null) {
        ListTag<StringTag> faces = new ListTag<>();
        for (BlockFace face : filter.allowedFaces()) {
            faces.add(new StringTag("", face.name()));
        }
        result.putList("allowed_faces", faces);
    }

    // block_filter: List<String> (block identifiers)
    if (filter.blockFilter() != null) {
        ListTag<StringTag> blocks = new ListTag<>();
        for (String block : filter.blockFilter()) {
            blocks.add(new StringTag("", block));
        }
        result.putList("block_filter", blocks);
    }

    return result;
}
```

**参考**: `reference/ECProEntity/.../ComponentPlacementFilter.java`

#### 1.6.3 Liquid Detection
```java
private static CompoundTag convertLiquidDetection(LiquidDetection detection) {
    CompoundTag result = new CompoundTag();
    ListTag<CompoundTag> rules = new ListTag<>();

    if (detection.detectionRules() != null) {
        for (DetectionRule rule : detection.detectionRules()) {
            CompoundTag ruleTag = new CompoundTag();

            // liquid_type: water, lava
            ruleTag.putString("liquidType", rule.liquidType().name());

            // on_liquid_touches: blocking, containment
            ruleTag.putString("onLiquidTouches", rule.onLiquidTouches().name());

            // stops_liquid_from_direction: bit mask
            int directions = 0;
            if (rule.stopsLiquidFromDirection() != null) {
                for (Direction direction : rule.stopsLiquidFromDirection()) {
                    directions |= 1 << direction.ordinal();
                }
            }
            ruleTag.putByte("stopsLiquidFromDirection", (byte) directions);

            // can_contain_liquid
            ruleTag.putBoolean("canContainLiquid", rule.canContainLiquid());

            rules.add(ruleTag);
        }
    }

    result.putList("detectionRules", rules);
    return result;
}
```

**参考**: `reference/ECProEntity/.../BlockComponents.java:243-275`

#### 1.6.4 Item Visual
```java
private static CompoundTag convertItemVisual(ItemVisual visual) {
    CompoundTag result = new CompoundTag();

    if (visual.geometry() != null) {
        result.putCompound("geometryDescription", convertGeometry(visual.geometry()));
    }

    if (visual.materialInstances() != null) {
        result.putCompound("materialInstancesDescription",
            convertMaterialInstances(visual.materialInstances()));
    }

    return result;
}
```

### 1.7 添加 BlockSerializer 重建

**状态**: 待实现
**文件**: `adapter-easecation/.../EasecationRegistry.java`

```java
@Override
public void registerBlocks(List<BlockDef> blocks) {
    log.info("[EaseCation] registerBlocks: Starting registration of " + blocks.size() + " blocks");

    for (BlockDef blockDef : blocks) {
        // ... 注册逻辑 ...
    }

    // 重建方块调色板和物品映射
    try {
        BlockSerializer.rebuildPalette();
        ItemSerializer.rebuildRuntimeMapping();
        log.info("[EaseCation] Block palette and item mappings rebuilt successfully");
    } catch (Exception e) {
        log.severe("[EaseCation] Failed to rebuild block palette: " + e.getMessage());
        e.printStackTrace();
    }

    log.info("[EaseCation] Block registration completed");
}
```

**注意**: 需要确认 EaseCation Nukkit 分支是否有这些 API

### 1.8 测试用例

创建测试行为包 `test-addon-pack.zip`:

```json
// BP/blocks/test_block.json
{
  "format_version": "1.21.60",
  "minecraft:block": {
    "description": {
      "identifier": "test:custom_block",
      "menu_category": {
        "category": "construction"
      },
      "states": {
        "test:color": ["red", "green", "blue"],
        "test:powered": [false, true]
      },
      "traits": {
        "minecraft:placement_direction": {
          "enabled_states": ["minecraft:cardinal_direction"],
          "y_rotation_offset": 0
        }
      }
    },
    "components": {
      "minecraft:destructible_by_mining": {
        "seconds_to_destroy": 2.0
      },
      "minecraft:light_emission": 15,
      "minecraft:geometry": "geometry.custom_block",
      "minecraft:material_instances": {
        "*": {
          "texture": "custom_texture",
          "render_method": "opaque"
        }
      }
    },
    "permutations": [
      {
        "condition": "q.block_state('test:powered') == true",
        "components": {
          "minecraft:light_emission": 0
        }
      }
    ]
  }
}
```

**测试步骤**:
1. 加载测试行为包
2. 验证方块注册成功
3. 放置方块并检查：
   - 光照等级
   - 旋转功能
   - 状态切换
   - Permutation 生效

---

## 阶段二：实体核心功能

**目标**: 实现完整的实体组件和高级功能
**时间**: 第3-4周
**优先级**: 🔴 高

### 2.1 Rideable 骑乘系统

**状态**: 待实现
**文件**: `adapter-easecation/.../EntityDataDriven.java`

#### 2.1.1 实现 EntityRideable 接口
```java
public class EntityDataDriven extends EntityCreature implements EntityRideable {

    private RideableConfig rideableConfig;

    // EntityRideable 接口方法
    @Override
    public boolean mountEntity(Entity entity) {
        return mountEntity(entity, 0);
    }

    @Override
    public boolean mountEntity(Entity entity, byte mode) {
        if (rideableConfig == null || entity == null) {
            return false;
        }

        // 检查座位数量限制
        if (this.passengers.size() >= rideableConfig.seatCount) {
            return false;
        }

        // 检查是否允许交互
        if (!rideableConfig.interactText.isEmpty() && !(entity instanceof Player)) {
            return false;
        }

        // 执行骑乘
        return entity.riding = this;
    }

    @Override
    public Vector3f getMountedOffset(Entity entity) {
        if (rideableConfig == null || rideableConfig.seats.isEmpty()) {
            return new Vector3f(0, 0, 0);
        }

        // 获取乘客索引
        int seatIndex = this.passengers.indexOf(entity);
        if (seatIndex < 0 || seatIndex >= rideableConfig.seats.size()) {
            seatIndex = 0;
        }

        Seat seat = rideableConfig.seats.get(seatIndex);
        return new Vector3f(
            seat.position[0],
            seat.position[1],
            seat.position[2]
        );
    }
}
```

#### 2.1.2 RideableConfig 数据结构
```java
public static class RideableConfig {
    int seatCount;
    boolean crouchingSkipInteract;
    boolean controllerPlayerSeatsOnly;
    String interactText;
    List<Seat> seats;

    public static class Seat {
        float[] position = new float[]{0, 0, 0};
        float[] rotation = new float[]{0, 0};
        boolean lockRiderRotation;
        float rotationOffsetDegrees;
        int minRiderCount;
        int maxRiderCount;
    }
}
```

#### 2.1.3 解析 minecraft:rideable 组件
```java
private void initComponents() {
    // ... 其他组件 ...

    // Rideable 组件
    if (components.minecraft_rideable != null) {
        var rideable = components.minecraft_rideable;

        rideableConfig = new RideableConfig();
        rideableConfig.seatCount = rideable.seatCount() != null ? rideable.seatCount() : 1;
        rideableConfig.crouchingSkipInteract = rideable.crouchingSkipInteract() != null
            ? rideable.crouchingSkipInteract() : false;
        rideableConfig.controllerPlayerSeatsOnly = rideable.controllerPlayerSeatsOnly() != null
            ? rideable.controllerPlayerSeatsOnly() : true;
        rideableConfig.interactText = rideable.interactText() != null ? rideable.interactText() : "";

        // 解析座位配置
        if (rideable.seats() != null) {
            rideableConfig.seats = new ArrayList<>();
            for (var seatDTO : rideable.seats()) {
                RideableConfig.Seat seat = new RideableConfig.Seat();

                if (seatDTO.position() != null && seatDTO.position().size() >= 3) {
                    seat.position[0] = seatDTO.position().get(0).floatValue();
                    seat.position[1] = seatDTO.position().get(1).floatValue();
                    seat.position[2] = seatDTO.position().get(2).floatValue();
                }

                if (seatDTO.rotation() != null && seatDTO.rotation().size() >= 2) {
                    seat.rotation[0] = seatDTO.rotation().get(0).floatValue();
                    seat.rotation[1] = seatDTO.rotation().get(1).floatValue();
                }

                seat.lockRiderRotation = seatDTO.lockRiderRotation() != null
                    ? seatDTO.lockRiderRotation() : false;
                seat.rotationOffsetDegrees = seatDTO.rotateRiderBy() != null
                    ? seatDTO.rotateRiderBy().floatValue() : 0;

                rideableConfig.seats.add(seat);
            }
        }
    }
}
```

#### 2.1.4 实现 onInteract 触发骑乘
```java
@Override
public boolean onInteract(Player player, Item item) {
    if (rideableConfig != null && !rideableConfig.interactText.isEmpty()) {
        // 蹲下时跳过交互
        if (rideableConfig.crouchingSkipInteract && player.isSneaking()) {
            return false;
        }

        // 尝试骑乘
        if (player.riding == null) {
            return mountEntity(player);
        }
    }

    return super.onInteract(player, item);
}
```

**参考**: `reference/ECProEntity/.../EntityDataDriven.java:172-230`

### 2.2 Damage Sensor 伤害系统

**状态**: 待实现
**文件**: `adapter-easecation/.../EntityDataDriven.java`

#### 2.2.1 DamageSensorConfig 数据结构
```java
public static class DamageSensorConfig {
    List<DamageTrigger> triggers;

    public static class DamageTrigger {
        DamageCause cause; // 伤害类型
        boolean dealsDamage = true; // 是否造成伤害
        float damageModifier = 0.0f; // 伤害修改器（加法）
        float damageMultiplier = 1.0f; // 伤害倍率（乘法）
        String onDamageEvent; // 触发的事件
    }
}
```

#### 2.2.2 解析 minecraft:damage_sensor
```java
private DamageSensorConfig damageSensorConfig;

private void initComponents() {
    // ... 其他组件 ...

    // Damage Sensor
    if (components.minecraft_damageSensor != null) {
        damageSensorConfig = new DamageSensorConfig();
        damageSensorConfig.triggers = new ArrayList<>();

        var sensors = components.minecraft_damageSensor;
        if (sensors.triggers() != null) {
            for (var triggerDTO : sensors.triggers()) {
                DamageSensorConfig.DamageTrigger trigger =
                    new DamageSensorConfig.DamageTrigger();

                trigger.cause = triggerDTO.cause() != null
                    ? DamageCause.valueOf(triggerDTO.cause()) : null;
                trigger.dealsDamage = triggerDTO.dealsDamage() != null
                    ? triggerDTO.dealsDamage() : true;
                trigger.damageModifier = triggerDTO.damageModifier() != null
                    ? triggerDTO.damageModifier().floatValue() : 0.0f;
                trigger.damageMultiplier = triggerDTO.damageMultiplier() != null
                    ? triggerDTO.damageMultiplier().floatValue() : 1.0f;
                trigger.onDamageEvent = triggerDTO.onDamage();

                damageSensorConfig.triggers.add(trigger);
            }
        }
    }
}
```

#### 2.2.3 实现 attack() 方法
```java
@Override
public boolean attack(EntityDamageEvent source) {
    if (source == null || source.isCancelled()) {
        return false;
    }

    // 应用 damage_sensor 逻辑
    if (damageSensorConfig != null && !damageSensorConfig.triggers.isEmpty()) {
        DamageCause cause = source.getCause();

        for (DamageSensorConfig.DamageTrigger trigger : damageSensorConfig.triggers) {
            // 匹配伤害类型（null = 匹配所有）
            if (trigger.cause == null || trigger.cause == cause) {
                // 是否造成伤害
                if (!trigger.dealsDamage) {
                    source.setCancelled(true);
                    return false;
                }

                // 应用伤害修改器和倍率
                float damage = source.getDamage();
                damage = (damage + trigger.damageModifier) * trigger.damageMultiplier;
                source.setDamage(damage);

                // 触发事件
                if (trigger.onDamageEvent != null) {
                    // TODO: 触发 minecraft 事件系统
                }

                break; // 只应用第一个匹配的触发器
            }
        }
    }

    // 应用击退抗性
    if (knockbackResistance > 0 && source instanceof EntityDamageByEntityEvent) {
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) source;
        if (event.getKnockBack() > 0) {
            float reducedKnockback = event.getKnockBack() * (1.0f - knockbackResistance);
            event.setKnockBack(reducedKnockback);
        }
    }

    return super.attack(source);
}
```

**参考**: `reference/ECProEntity/.../EntityDataDriven.java:238-268`

### 2.3 Physics 和基础组件

**状态**: 待实现
**文件**: `adapter-easecation/.../EntityDataDriven.java`

#### 2.3.1 Physics 组件
```java
private boolean hasGravity = true;
private boolean hasCollision = true;

private void initComponents() {
    // ... 其他组件 ...

    // Physics
    if (components.minecraft_physics != null) {
        var physics = components.minecraft_physics;
        hasGravity = physics.hasGravity() != null ? physics.hasGravity() : true;
        hasCollision = physics.hasCollision() != null ? physics.hasCollision() : true;
    }
}

@Override
public float getGravity() {
    return hasGravity ? super.getGravity() : 0.0f;
}

@Override
public boolean canCollide() {
    return hasCollision;
}
```

#### 2.3.2 Variant / Mark Variant / Skin ID
```java
private int variant = 0;
private int markVariant = 0;
private int skinId = 0;

private void initComponents() {
    // Variant
    if (components.minecraft_variant != null && components.minecraft_variant.value() != null) {
        this.variant = components.minecraft_variant.value();
        this.setDataProperty(new IntEntityData(DATA_VARIANT, variant));
    }

    // Mark Variant
    if (components.minecraft_markVariant != null && components.minecraft_markVariant.value() != null) {
        this.markVariant = components.minecraft_markVariant.value();
        this.setDataProperty(new IntEntityData(DATA_MARK_VARIANT, markVariant));
    }

    // Skin ID
    if (components.minecraft_skinId != null && components.minecraft_skinId.value() != null) {
        this.skinId = components.minecraft_skinId.value();
        this.setDataProperty(new IntEntityData(DATA_SKIN_ID, skinId));
    }
}
```

#### 2.3.3 Pushable 组件
```java
private boolean isPushable = true;
private boolean isPushableByPiston = true;

private void initComponents() {
    // Pushable
    if (components.minecraft_pushable != null) {
        var pushable = components.minecraft_pushable;
        isPushable = pushable.isPushable() != null ? pushable.isPushable() : true;
        isPushableByPiston = pushable.isPushableByPiston() != null
            ? pushable.isPushableByPiston() : true;
    }
}

@Override
public boolean canBePushed() {
    return isPushable;
}

public boolean canBePushedByPiston() {
    return isPushableByPiston;
}
```

#### 2.3.4 Is Baby 组件
```java
private boolean isBaby = false;

private void initComponents() {
    // Is Baby
    if (components.minecraft_isBaby != null) {
        isBaby = true;
        this.setDataFlag(DATA_FLAGS, DATA_FLAG_BABY, true);
        this.setScale(0.5f); // 幼体缩放为50%
    }
}
```

#### 2.3.5 Knockback Resistance 组件
```java
private float knockbackResistance = 0.0f;

private void initComponents() {
    // Knockback Resistance
    if (components.minecraft_knockbackResistance != null) {
        var resistance = extractRangeValue(components.minecraft_knockbackResistance.value());
        if (resistance != null) {
            knockbackResistance = resistance.floatValue();
        }
    }
}
```

**参考**: `reference/ECProEntity/.../EntityDataDriven.java:87-140`

### 2.4 网络和视距管理

**状态**: 待实现
**文件**: `adapter-easecation/.../EntityDataDriven.java`

#### 2.4.1 自定义生成包
```java
@Override
public void spawnTo(Player player) {
    if (this.hasSpawned.containsKey(player.getLoaderId())) {
        return;
    }

    this.hasSpawned.put(player.getLoaderId(), player);

    AddEntityPacket pk = new AddEntityPacket();
    pk.entityUniqueId = this.getId();
    pk.entityRuntimeId = this.getId();
    pk.type = this.getNetworkId();
    pk.x = (float) this.x;
    pk.y = (float) this.y;
    pk.z = (float) this.z;
    pk.speedX = (float) this.motionX;
    pk.speedY = (float) this.motionY;
    pk.speedZ = (float) this.motionZ;
    pk.yaw = (float) this.yaw;
    pk.pitch = (float) this.pitch;
    pk.headYaw = (float) this.headYaw;
    pk.metadata = this.dataProperties;
    pk.links = new EntityLink[0]; // TODO: 添加骑乘链接

    player.dataPacket(pk);

    // 发送属性和装备
    this.sendPotionEffects(player);
    this.sendData(player, this.dataProperties);
}
```

#### 2.4.2 视距管理
```java
private int ticksSinceLastCheck = 0;
private static final int VIEW_DISTANCE_CHECK_INTERVAL = 20; // 1秒检查一次

@Override
public boolean onUpdate(int currentTick) {
    if (this.closed) {
        return false;
    }

    // 定期检查玩家视距
    ticksSinceLastCheck++;
    if (ticksSinceLastCheck >= VIEW_DISTANCE_CHECK_INTERVAL) {
        ticksSinceLastCheck = 0;
        checkPlayerViewDistance();
    }

    return super.onUpdate(currentTick);
}

private void checkPlayerViewDistance() {
    if (this.chunk == null) {
        return;
    }

    for (Player player : this.level.getPlayers().values()) {
        double distance = player.distance(this);
        int viewDistance = player.getViewDistance() * 16; // 区块到方块

        boolean hasSpawned = this.hasSpawned.containsKey(player.getLoaderId());

        if (distance <= viewDistance && !hasSpawned) {
            // 玩家进入视距，生成实体
            this.spawnTo(player);
        } else if (distance > viewDistance && hasSpawned) {
            // 玩家离开视距，移除实体
            this.despawnFrom(player);
        }
    }
}
```

**参考**: `reference/ECProEntity/.../EntityDataDriven.java:270-310`

### 2.5 其他实体组件

#### 2.5.1 Flying Speed
```java
private float flyingSpeed = 0.02f;

private void initComponents() {
    // Flying Speed
    if (components.minecraft_flyingSpeed != null) {
        var speed = extractRangeValue(components.minecraft_flyingSpeed.value());
        if (speed != null) {
            flyingSpeed = speed.floatValue();
        }
    }
}
```

#### 2.5.2 Can Fly / Can Climb
```java
private boolean canFly = false;
private boolean canClimb = false;

private void initComponents() {
    // Can Fly
    if (components.minecraft_canFly != null) {
        canFly = true;
        this.setDataFlag(DATA_FLAGS, DATA_FLAG_CAN_FLY, true);
    }

    // Can Climb
    if (components.minecraft_canClimb != null) {
        canClimb = true;
    }
}

@Override
public boolean canClimb() {
    return canClimb;
}
```

#### 2.5.3 Breathable
```java
private boolean breathable = true;
private List<String> breathableBlocks = new ArrayList<>();

private void initComponents() {
    // Breathable
    if (components.minecraft_breathable != null) {
        var breathable = components.minecraft_breathable;
        this.breathable = breathable.breathesAir() != null ? breathable.breathesAir() : true;

        if (breathable.breatheBlocks() != null) {
            breathableBlocks.addAll(breathable.breatheBlocks());
        }
    }
}
```

#### 2.5.4 Is Immobile
```java
private boolean isImmobile = false;

private void initComponents() {
    // Is Immobile
    if (components.minecraft_isImmobile != null) {
        isImmobile = true;
        this.setImmobile(true);
    }
}
```

---

## 阶段三：资源包和生物群系

**目标**: 实现资源包发送和生物群系支持
**时间**: 第5周
**优先级**: 🟡 中

### 3.1 资源包发送机制

**状态**: 待实现
**文件**: `adapter-easecation/.../EasecationRegistry.java`

#### 3.1.1 资源包映射
```java
public class EasecationRegistry implements AddonRegistry {
    private final Logger log;
    private static final Capabilities CAPS = new Capabilities(true);

    // 资源包映射
    private final Map<String, ResourcePackInfo> resourcePackMap = new ConcurrentHashMap<>();

    public static class ResourcePackInfo {
        String uuid;
        String version;
        String url;
        String sha256;
        long size;
    }
}
```

#### 3.1.2 写入资源包文件
```java
public void deployResourcePacks(List<AddonPack> packs, Path resourcePackDir) {
    for (AddonPack pack : packs) {
        if (pack.resourceFiles().isEmpty()) {
            continue;
        }

        try {
            // 生成资源包文件名
            String fileName = pack.manifest().getHeader().getUuid() + "_"
                + pack.manifest().getHeader().getVersion() + ".zip";
            Path packFile = resourcePackDir.resolve(fileName);

            // 写入ZIP文件
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(packFile.toFile()))) {
                for (Map.Entry<String, byte[]> entry : pack.resourceFiles().entrySet()) {
                    ZipEntry zipEntry = new ZipEntry(entry.getKey());
                    zos.putNextEntry(zipEntry);
                    zos.write(entry.getValue());
                    zos.closeEntry();
                }
            }

            // 计算SHA256
            String sha256 = calculateSHA256(packFile);

            // 保存映射信息
            ResourcePackInfo info = new ResourcePackInfo();
            info.uuid = pack.manifest().getHeader().getUuid();
            info.version = pack.manifest().getHeader().getVersion();
            info.url = packFile.toUri().toString(); // 或者CDN URL
            info.sha256 = sha256;
            info.size = Files.size(packFile);

            resourcePackMap.put(info.uuid, info);

            log.info("[EaseCation] Deployed resource pack: " + fileName);

        } catch (Exception e) {
            log.severe("[EaseCation] Failed to deploy resource pack: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

private String calculateSHA256(Path file) throws Exception {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    try (InputStream is = Files.newInputStream(file)) {
        byte[] buffer = new byte[8192];
        int read;
        while ((read = is.read(buffer)) > 0) {
            digest.update(buffer, 0, read);
        }
    }
    byte[] hash = digest.digest();
    StringBuilder hexString = new StringBuilder();
    for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
    }
    return hexString.toString();
}
```

#### 3.1.3 事件监听器
```java
public class ResourcePackListener implements Listener {
    private final Map<String, ResourcePackInfo> resourcePackMap;

    public ResourcePackListener(Map<String, ResourcePackInfo> resourcePackMap) {
        this.resourcePackMap = resourcePackMap;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 发送资源包请求
        for (ResourcePackInfo info : resourcePackMap.values()) {
            ResourcePackDataInfoPacket pk = new ResourcePackDataInfoPacket();
            pk.packId = info.uuid;
            pk.packVersion = info.version;
            pk.maxChunkSize = 1048576; // 1MB
            pk.chunkCount = (int) Math.ceil(info.size / 1048576.0);
            pk.compressedPackSize = info.size;
            pk.sha256 = info.sha256;

            player.dataPacket(pk);
        }
    }

    @EventHandler
    public void onResourcePackResponse(PlayerResourcePackResponseEvent event) {
        Player player = event.getPlayer();
        String packId = event.getPackId();

        switch (event.getStatus()) {
            case REFUSED:
                player.kick("You must accept the resource pack to play on this server");
                break;
            case SEND_PACKS:
                // 发送资源包数据块
                sendResourcePackChunks(player, packId);
                break;
            case HAVE_ALL_PACKS:
                // 玩家已有所有资源包
                break;
            case COMPLETED:
                // 资源包加载完成
                break;
        }
    }

    private void sendResourcePackChunks(Player player, String packId) {
        ResourcePackInfo info = resourcePackMap.get(packId);
        if (info == null) {
            return;
        }

        try {
            Path packFile = Paths.get(new URI(info.url));
            byte[] data = Files.readAllBytes(packFile);

            int chunkSize = 1048576; // 1MB
            int chunkCount = (int) Math.ceil(data.length / (double) chunkSize);

            for (int i = 0; i < chunkCount; i++) {
                int offset = i * chunkSize;
                int length = Math.min(chunkSize, data.length - offset);
                byte[] chunk = Arrays.copyOfRange(data, offset, offset + length);

                ResourcePackChunkDataPacket pk = new ResourcePackChunkDataPacket();
                pk.packId = packId;
                pk.packVersion = info.version;
                pk.chunkIndex = i;
                pk.data = chunk;
                pk.progress = offset + length;

                player.dataPacket(pk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

**参考**: `reference/ECProEntity/.../ECProEntityPlugin.java:156-200`

### 3.2 生物群系支持

**状态**: 待实现

#### 3.2.1 BiomeDataDriven 类
**文件**: `adapter-easecation/.../biome/BiomeDataDriven.java` (新建)

```java
package net.easecation.bridge.adapter.easecation.biome;

import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.biome.Biomes;
import net.easecation.bridge.core.BiomeDef;
import net.easecation.bridge.core.dto.v1_21_60.behavior.biomes.Components;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data-driven custom biome implementation for EaseCation Nukkit.
 */
public class BiomeDataDriven extends Biome {

    // Static registry to map biome ID to BiomeDef
    private static final Map<Integer, BiomeDef> BIOME_DEF_REGISTRY = new ConcurrentHashMap<>();

    private String identifier;
    private Components components;

    public BiomeDataDriven(int id) {
        super(id);
        BiomeDef biomeDef = BIOME_DEF_REGISTRY.get(id);
        if (biomeDef == null) {
            throw new RuntimeException("BiomeDataDriven: BiomeDef not found for id " + id);
        }

        this.identifier = biomeDef.id();
        this.components = biomeDef.components();

        initComponents();
    }

    /**
     * Register a BiomeDef for a given biome ID.
     */
    public static void registerBiomeDef(int id, BiomeDef biomeDef) {
        BIOME_DEF_REGISTRY.put(id, biomeDef);
    }

    private void initComponents() {
        if (components == null) {
            return;
        }

        // Climate (temperature)
        if (components.minecraft_climate != null && components.minecraft_climate.temperature() != null) {
            this.setTemperature(components.minecraft_climate.temperature().floatValue());
        }

        // Humidity
        if (components.minecraft_humidity != null && components.minecraft_humidity.value() != null) {
            this.setHumidity(components.minecraft_humidity.value().floatValue());
        }

        // Map Tints
        if (components.minecraft_mapTints != null) {
            var tints = components.minecraft_mapTints;
            // TODO: 设置水颜色、草颜色、树叶颜色等
        }
    }

    @Override
    public String getName() {
        return identifier;
    }
}
```

#### 3.2.2 BiomeComponents NBT
**文件**: `adapter-easecation/.../biome/BiomeComponentsNBT.java` (新建)

```java
package net.easecation.bridge.adapter.easecation.biome;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.StringTag;
import net.easecation.bridge.core.dto.v1_21_60.behavior.biomes.Components;

public class BiomeComponentsNBT {

    public static CompoundTag toNBT(Components components) {
        if (components == null) {
            return new CompoundTag();
        }

        CompoundTag tag = new CompoundTag();

        // Tags
        if (components.minecraft_tags != null && components.minecraft_tags.tags() != null) {
            ListTag<StringTag> tags = new ListTag<>();
            for (String t : components.minecraft_tags.tags()) {
                tags.add(new StringTag("", t));
            }
            tag.putCompound("minecraft:tags", new CompoundTag().putList("tags", tags));
        }

        // Climate
        if (components.minecraft_climate != null) {
            CompoundTag climate = new CompoundTag();
            if (components.minecraft_climate.temperature() != null) {
                climate.putFloat("temperature", components.minecraft_climate.temperature().floatValue());
            }
            if (components.minecraft_climate.downfall() != null) {
                climate.putFloat("downfall", components.minecraft_climate.downfall().floatValue());
            }
            tag.putCompound("minecraft:climate", climate);
        }

        // Humidity
        if (components.minecraft_humidity != null && components.minecraft_humidity.value() != null) {
            tag.putCompound("minecraft:humidity",
                new CompoundTag().putFloat("value", components.minecraft_humidity.value().floatValue()));
        }

        // Map Tints
        if (components.minecraft_mapTints != null) {
            CompoundTag tints = new CompoundTag();
            var mt = components.minecraft_mapTints;

            if (mt.waterColor() != null) {
                tints.putString("water_color", mt.waterColor());
            }
            if (mt.waterSurfaceColor() != null) {
                tints.putString("water_surface_color", mt.waterSurfaceColor());
            }
            if (mt.grassColor() != null) {
                tints.putString("grass_color", mt.grassColor());
            }
            if (mt.foliageColor() != null) {
                tints.putString("foliage_color", mt.foliageColor());
            }

            tag.putCompound("minecraft:map_tints", tints);
        }

        // Creature Spawn Probability
        if (components.minecraft_creatureSpawnProbability != null) {
            var probability = components.minecraft_creatureSpawnProbability;
            CompoundTag spawn = new CompoundTag();

            if (probability.defaultSpawnProbability() != null) {
                spawn.putFloat("default", probability.defaultSpawnProbability().floatValue());
            }

            // TODO: 添加生物类型概率

            tag.putCompound("minecraft:creature_spawn_probability", spawn);
        }

        return tag;
    }
}
```

#### 3.2.3 注册生物群系
**文件**: `adapter-easecation/.../EasecationRegistry.java`

```java
@Override
public void registerBiomes(List<BiomeDef> biomes) {
    log.info("[EaseCation] registerBiomes: Starting registration of " + biomes.size() + " biomes");

    for (BiomeDef biomeDef : biomes) {
        try {
            // 1. Allocate biome ID
            int biomeId = Biomes.getAvailableBiomeId();

            // 2. Register to BiomeDataDriven registry
            BiomeDataDriven.registerBiomeDef(biomeId, biomeDef);

            // 3. Build NBT data
            CompoundTag nbt = buildBiomeNBT(biomeDef);

            // 4. Register custom biome
            Biomes.registerCustomBiome(
                biomeId,
                biomeDef.id(),
                BiomeDataDriven.class,
                nbt
            );

            log.info("[EaseCation] Registered biome: " + biomeDef.id() + " (id=" + biomeId + ")");

        } catch (Exception e) {
            log.severe("[EaseCation] Failed to register biome: " + biomeDef.id() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }

    log.info("[EaseCation] Biome registration completed");
}

private CompoundTag buildBiomeNBT(BiomeDef biomeDef) {
    CompoundTag nbt = new CompoundTag();

    nbt.putString("identifier", biomeDef.id());

    // Components
    if (biomeDef.components() != null) {
        CompoundTag componentsNBT = BiomeComponentsNBT.toNBT(biomeDef.components());
        for (String key : componentsNBT.getTags().keySet()) {
            nbt.put(key, componentsNBT.get(key));
        }
    }

    return nbt;
}
```

#### 3.2.4 扩展 AddonParser
**文件**: `addon-bridge-core/.../AddonParser.java`

```java
// 添加 BiomeDef 解析
private AddonPack parseZipPack(File zipFile) throws IOException {
    List<BiomeDef> biomes = new ArrayList<>();

    try (ZipFile zip = new ZipFile(zipFile)) {
        // ... 解析 manifest, blocks, entities ...

        // Parse biomes/*.json
        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String name = entry.getName();

            if (name.startsWith("biomes/") && name.endsWith(".json")) {
                String json = new String(zip.getInputStream(entry).readAllBytes(), StandardCharsets.UTF_8);

                // Parse wrapper: { "minecraft:biome": { ... } }
                Map<String, Object> wrapper = MAPPER.readValue(json, Map.class);
                if (wrapper.containsKey("minecraft:biome")) {
                    Object biomeData = wrapper.get("minecraft:biome");
                    BiomeDefinitions biomeDef = MAPPER.convertValue(biomeData, BiomeDefinitions.class);
                    biomes.add(BiomeDef.fromDTO(biomeDef));
                }
            }
        }
    }

    return new AddonPack(manifest, blocks, entities, biomes, items, recipes, resourceFiles);
}
```

**参考**: `reference/ECProEntity/.../BedrockAddonLoader.java:212-237`

---

## 阶段四：测试和优化

**目标**: 确保功能正常运行并优化性能
**时间**: 第6周
**优先级**: 🟡 中

### 4.1 单元测试

**文件**: `adapter-easecation/src/test/java/...` (新建测试目录)

#### 4.1.1 BlockComponentsNBT 测试
```java
@Test
public void testLightEmissionNBT() {
    Component component = new Component(
        null, null, null, null, null, null, null, null, null, null, null,
        new LightEmission(15), // light_emission
        null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null
    );

    CompoundTag nbt = BlockComponentsNBT.toNBT(component, false);

    // 验证多协议版本
    assertTrue(nbt.contains("minecraft:block_light_emission"));
    assertTrue(nbt.contains("minecraft:light_emission"));

    assertEquals(1.0f, nbt.getCompound("minecraft:block_light_emission").getFloat("emission"), 0.01f);
    assertEquals(15, nbt.getCompound("minecraft:light_emission").getByte("emission"));
}

@Test
public void testCollisionBoxNBT() {
    // Boolean variant
    CollisionBox box1 = new CollisionBox.CollisionBox_Variant0(true);
    Component component1 = new Component(box1, null, ...);

    CompoundTag nbt1 = BlockComponentsNBT.toNBT(component1, false);
    assertTrue(nbt1.contains("minecraft:collision_box"));

    // Detailed variant
    CollisionBox box2 = new CollisionBox.CollisionBox_Variant1(
        List.of(-4, 0, -4),  // origin
        List.of(8, 16, 8)    // size
    );
    Component component2 = new Component(box2, null, ...);

    CompoundTag nbt2 = BlockComponentsNBT.toNBT(component2, false);
    CompoundTag collisionBox = nbt2.getCompound("minecraft:collision_box");
    assertEquals(3, collisionBox.getList("origin").size());
    assertEquals(3, collisionBox.getList("size").size());
}
```

#### 4.1.2 BlockTraits 测试
```java
@Test
public void testPlacementDirectionNBT() {
    // TODO: 实现 trait 测试
}
```

#### 4.1.3 Entity 组件测试
```java
@Test
public void testRideableComponent() {
    // TODO: 实现骑乘测试
}

@Test
public void testDamageSensorComponent() {
    // TODO: 实现伤害感应器测试
}
```

### 4.2 集成测试

#### 4.2.1 创建测试行为包

**目录结构**:
```
test-addon-pack/
├── manifest.json
├── blocks/
│   ├── glowing_block.json (发光方块)
│   ├── rotatable_block.json (可旋转方块)
│   └── liquid_block.json (液体检测方块)
├── entities/
│   ├── rideable_entity.json (可骑乘实体)
│   └── tough_entity.json (高防御实体)
├── biomes/
│   └── custom_biome.json (自定义生物群系)
└── resource_pack/
    ├── manifest.json
    ├── textures/
    └── models/
```

#### 4.2.2 测试用例

**方块测试**:
1. 加载行为包，验证方块注册成功
2. 放置发光方块，验证光照等级为15
3. 旋转方块，验证方向正确
4. 放置液体方块，验证液体检测生效
5. 检查方块NBT数据是否正确

**实体测试**:
1. 生成可骑乘实体
2. 玩家交互骑乘，验证骑乘成功
3. 验证座位偏移和旋转锁定
4. 攻击高防御实体，验证伤害减免
5. 验证实体视距管理（玩家靠近/远离）

**生物群系测试**:
1. 创建包含自定义生物群系的世界
2. 验证温度、湿度正确
3. 验证地图颜色正确

#### 4.2.3 测试服务器配置

在 `workspace/easecation/` 中运行测试：
```bash
# 启动服务器
cd workspace/easecation
java -jar nukkit.jar

# 加载测试行为包
cp test-addon-pack.zip server/worlds/world/behavior_packs/

# 连接并测试
```

### 4.3 性能优化

#### 4.3.1 碰撞箱计算缓存
```java
public class BlockDataDriven extends CustomBlock {
    // 缓存碰撞箱计算结果
    private AxisAlignedBB cachedBoundingBox;
    private boolean boundingBoxDirty = true;

    @Override
    protected AxisAlignedBB recalculateBoundingBox() {
        if (!boundingBoxDirty && cachedBoundingBox != null) {
            return cachedBoundingBox;
        }

        // 重新计算
        cachedBoundingBox = calculateBoundingBox();
        boundingBoxDirty = false;

        return cachedBoundingBox;
    }

    @Override
    public void setPosition(Vector3 pos) {
        super.setPosition(pos);
        boundingBoxDirty = true; // 位置变化，标记为脏
    }
}
```

#### 4.3.2 实体视距管理优化
```java
// 使用空间分区优化玩家查询
private void checkPlayerViewDistance() {
    if (this.chunk == null) {
        return;
    }

    // 只检查附近区块的玩家
    int chunkX = this.chunk.getX();
    int chunkZ = this.chunk.getZ();
    int viewChunks = 8; // 假设最大视距为8区块

    for (int x = chunkX - viewChunks; x <= chunkX + viewChunks; x++) {
        for (int z = chunkZ - viewChunks; z <= chunkZ + viewChunks; z++) {
            Chunk chunk = this.level.getChunk(x, z);
            if (chunk != null) {
                for (Entity entity : chunk.getEntities().values()) {
                    if (entity instanceof Player) {
                        checkPlayerDistance((Player) entity);
                    }
                }
            }
        }
    }
}
```

#### 4.3.3 NBT构建性能
```java
// 缓存常用的NBT结构
private static final CompoundTag DEFAULT_GEOMETRY_NBT = new CompoundTag()
    .putString("identifier", "minecraft:geometry.full_block")
    .putString("culling", "")
    .putString("culling_layer", "minecraft:culling_layer.undefined")
    .putCompound("bone_visibility", new CompoundTag())
    .putBoolean("uv_lock", false);

// 避免重复创建相同的NBT
public static CompoundTag toNBT(Component component, boolean override) {
    CompoundTag tag = new CompoundTag();

    // 使用缓存的NBT
    if (component.minecraft_geometry() == null && !override) {
        tag.putCompound("minecraft:geometry", DEFAULT_GEOMETRY_NBT.copy());
    }

    // ...
}
```

#### 4.3.4 性能基准测试
```java
@Test
public void benchmarkBlockNBTGeneration() {
    Component component = createComplexComponent();

    long startTime = System.nanoTime();
    for (int i = 0; i < 10000; i++) {
        BlockComponentsNBT.toNBT(component, false);
    }
    long endTime = System.nanoTime();

    double avgTime = (endTime - startTime) / 10000.0 / 1_000_000.0; // ms
    System.out.println("Average NBT generation time: " + avgTime + " ms");

    // 目标：< 1ms per block
    assertTrue(avgTime < 1.0, "NBT generation too slow: " + avgTime + " ms");
}
```

### 4.4 文档和示例

#### 4.4.1 用户文档
**文件**: `docs/user-guide.md` (新建)

内容：
- 如何安装 nukkit-addon-bridge
- 如何创建行为包
- 支持的组件列表
- 常见问题解答

#### 4.4.2 开发文档
**文件**: `docs/developer-guide.md` (新建)

内容：
- 架构概述
- 如何添加新的adapter
- 如何扩展组件支持
- API 参考

#### 4.4.3 示例行为包
**文件**: `examples/sample-addon-pack/` (新建)

包含：
- 基础方块示例
- 基础实体示例
- 高级功能示例（骑乘、伤害处理等）
- 资源包示例

---

## 技术参考

### ECProEntity 关键文件

| 文件 | 路径 | 关键功能 |
|------|------|----------|
| BedrockAddonLoader | reference/ECProEntity/.../loader/BedrockAddonLoader.java | 行为包加载和注册 |
| BlockComponents | reference/ECProEntity/.../block/datadriven/BlockComponents.java | 方块组件NBT序列化 |
| BlockDefinition | reference/ECProEntity/.../block/datadriven/BlockDefinition.java | 方块定义数据结构 |
| BlockTraits | reference/ECProEntity/.../block/datadriven/BlockTraits.java | 方块特性 |
| BlockStates | reference/ECProEntity/.../block/datadriven/BlockStates.java | 方块状态 |
| BlockPermutation | reference/ECProEntity/.../block/datadriven/BlockPermutation.java | 方块排列组合 |
| EntityDataDriven | reference/ECProEntity/.../entity/datadriven/EntityDataDriven.java | 实体实现 |
| EntityComponents | reference/ECProEntity/.../entity/datadriven/EntityComponents.java | 实体组件 |
| BiomeDataDriven | reference/ECProEntity/.../biome/datadriven/BiomeDataDriven.java | 生物群系实现 |

### DTO 映射关系

| ECProEntity 类型 | 当前 DTO 类型 | 转换方式 |
|------------------|---------------|----------|
| `Optional<T>` | `@Nullable T` | 直接访问 |
| `Either<A, B>` | `sealed interface` | instanceof 匹配 |
| `@SerializedName` | `@JsonProperty` | 相同 |
| Gson | Jackson | MAPPER.readValue() |

### Nukkit API 参考

#### EaseCation Nukkit 特性
- `Blocks.allocateCustomBlockId()` - 分配自定义方块ID
- `Blocks.registerCustomBlock()` - 注册自定义方块
- `Entity.registerEntity()` - 注册实体（使用BiFunction）
- `Biomes.registerCustomBiome()` - 注册自定义生物群系
- `BlockSerializer.rebuildPalette()` - 重建方块调色板
- `ItemSerializer.rebuildRuntimeMapping()` - 重建物品映射

#### 实体数据常量
```java
DATA_FLAGS
DATA_FLAG_BABY
DATA_FLAG_CAN_FLY
DATA_VARIANT
DATA_MARK_VARIANT
DATA_SKIN_ID
DATA_BOUNDING_BOX_WIDTH
DATA_BOUNDING_BOX_HEIGHT
```

---

## 进度跟踪

### 已完成 ✅

- [x] 创建 BlockComponentsNBT 类 (430行)
- [x] 实现 17 个方块组件的NBT序列化
- [x] 更新 EasecationRegistry.buildBlockNBT()
- [x] 实现方块碰撞箱解析
- [x] 实现实体碰撞箱解析

### 进行中 🚧

- [ ] 创建完整的实施计划文档

### 待开始 📋

**阶段一：方块系统** (剩余工作)
- [ ] 创建 BlockTraits 类
- [ ] 创建 BlockStates 类
- [ ] 创建 BlockPermutation 类
- [ ] 扩展 BlockDefinition (50+ 字段)
- [ ] 完善 BlockComponentsNBT 的 TODO
- [ ] 添加 BlockSerializer.rebuildPalette()
- [ ] 方块集成测试

**阶段二：实体系统**
- [ ] 实现 Rideable 组件和骑乘逻辑
- [ ] 实现 Damage Sensor 和 attack() 方法
- [ ] 实现 Physics 组件
- [ ] 实现 Variant/MarkVariant/SkinID
- [ ] 实现 Pushable 组件
- [ ] 实现 Is Baby 组件
- [ ] 实现 Knockback Resistance
- [ ] 实现自定义生成包（spawnTo）
- [ ] 实现视距管理（onUpdate）
- [ ] 实体集成测试

**阶段三：扩展功能**
- [ ] 实现资源包写入
- [ ] 实现资源包事件监听器
- [ ] 创建 BiomeDataDriven 类
- [ ] 创建 BiomeComponentsNBT 类
- [ ] 实现生物群系注册
- [ ] 扩展 AddonParser 解析 biomes

**阶段四：测试和优化**
- [ ] 编写单元测试
- [ ] 创建测试行为包
- [ ] 集成测试
- [ ] 性能优化
- [ ] 编写文档
- [ ] 创建示例

### 工作量估算

| 阶段 | 任务数 | 预计时间 | 当前进度 |
|------|--------|----------|----------|
| 阶段一 | 10 个 | 2 周 | 30% |
| 阶段二 | 10 个 | 2 周 | 15% |
| 阶段三 | 6 个 | 1 周 | 0% |
| 阶段四 | 6 个 | 1 周 | 0% |
| **总计** | **32 个** | **6 周** | **~15%** |

---

## 附录

### A. 命名约定

- 类名：PascalCase (e.g., `BlockDataDriven`)
- 方法名：camelCase (e.g., `toNBT()`)
- 常量：UPPER_SNAKE_CASE (e.g., `BLOCK_DEF_REGISTRY`)
- 包名：lowercase (e.g., `net.easecation.bridge.adapter.easecation`)

### B. 代码风格

- 缩进：4空格
- 行宽：120字符
- 注释：JavaDoc 风格
- 空行：方法之间一行，逻辑块之间一行

### C. Git 提交规范

- feat: 新功能
- fix: 修复bug
- docs: 文档更新
- refactor: 重构
- test: 测试相关
- chore: 构建/工具链

示例：
```
feat: 实现BlockTraits类支持placement_direction

- 添加PlacementDirection支持cardinal和facing方向
- 添加PlacementPosition支持block_face和vertical_half
- 添加Connection支持cardinal_connections
- 集成到buildBlockNBT()方法

参考: ECProEntity BlockTraits.java:15-80
```

### D. 问题追踪

使用 GitHub Issues 追踪：
- Bug：实现中的错误
- Enhancement：功能增强
- Question：技术问题
- Documentation：文档相关

### E. 版本发布

- v0.1.0：阶段一完成（方块系统）
- v0.2.0：阶段二完成（实体系统）
- v0.3.0：阶段三完成（资源包和生物群系）
- v1.0.0：阶段四完成（测试和优化）

---

**文档维护**: 本文档将随着实施进度持续更新。
**最后更新**: 2025-10-28
**下次审查**: 阶段一完成后
