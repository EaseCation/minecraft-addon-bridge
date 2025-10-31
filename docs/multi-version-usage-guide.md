# 多版本支持系统使用指南

## 概述

Nukkit Addon Bridge 现在支持多版本 Minecraft Bedrock DTO，能够根据 behavior pack 的 `format_version` 自动加载和使用相应版本的数据结构。

## 架构概览

```
Behavior Pack JSON (format_version: 1.21.60)
           ↓
    EntityLoader (自动检测版本)
           ↓
  dto.entity.v1_21_60.Entity (版本特定 DTO)
           ↓
  EntityComponentsV1_21_60 (适配器)
           ↓
    IEntityComponents (版本无关接口)
           ↓
    EntityDef → EntityDataDriven
```

## 核心组件

### 1. FormatVersion - 版本号封装

```java
// 解析版本字符串
FormatVersion version = FormatVersion.parse("1.21.60");

// 转换为包名
String packageName = version.toPackageName();  // "v1_21_60"

// 版本比较
boolean isNewer = FormatVersion.V1_21_60.compareTo(FormatVersion.V1_19_0) > 0;

// 预定义常量
FormatVersion latest = FormatVersion.DEFAULT;  // 1.21.60
```

### 2. IEntityComponents - 版本无关接口

所有版本的实体组件通过统一接口访问：

```java
IEntityComponents components = entityDef.components();

// 获取通用组件属性
Double health = components.getHealthMax();
Double speed = components.getMovementSpeed();
Boolean hasGravity = components.getPhysicsHasGravity();

// 检查组件是否存在
if (components.hasComponent("minecraft:rideable")) {
    Integer seatCount = components.getRideableSeatCount();
}

// 获取原始 DTO 对象（用于版本特定的访问）
Object rawComponents = components.getRawComponents();
```

### 3. EntityLoader - 动态加载器

自动检测版本并加载对应的 DTO：

```java
import net.easecation.bridge.core.versioned.entity.EntityLoader;

// 方式1：从文件加载（自动检测版本）
EntityLoader.EntityData data = EntityLoader.load(Paths.get("behavior_pack/entities/custom_entity.json"));

// 方式2：从字符串加载（自动检测版本）
String json = Files.readString(entityJsonPath);
EntityLoader.EntityData data = EntityLoader.load(json);

// 方式3：指定版本加载
FormatVersion version = FormatVersion.V1_19_0;
EntityLoader.EntityData data = EntityLoader.load(json, version);

// 访问加载的数据
String identifier = data.identifier();
FormatVersion detectedVersion = data.version();
IEntityComponents components = data.components();
Object rawEntity = data.rawEntity();  // 原始 Entity DTO
```

### 4. EntityDef - 版本无关的实体定义

```java
import net.easecation.bridge.core.EntityDef;

// 创建 EntityDef（自动检测版本）
EntityDef def = EntityDef.fromJson(jsonContent);

// 访问属性
String id = def.id();
FormatVersion version = def.version();
IEntityComponents components = def.components();

// 使用统一接口访问组件
Double health = components.getHealthMax();
```

## 使用示例

### 示例 1：加载不同版本的实体

```java
// entities/zombie_v1.19.json (format_version: "1.19.0")
String oldEntityJson = """
{
  "format_version": "1.19.0",
  "minecraft:entity": {
    "description": {
      "identifier": "custom:zombie_old"
    },
    "components": {
      "minecraft:health": { "value": 20, "max": 20 }
    }
  }
}
""";

// entities/zombie_v1.21.json (format_version: "1.21.60")
String newEntityJson = """
{
  "format_version": "1.21.60",
  "minecraft:entity": {
    "description": {
      "identifier": "custom:zombie_new"
    },
    "components": {
      "minecraft:health": { "value": 20, "max": 20 }
    }
  }
}
""";

// 两个版本都能正确加载
EntityDef oldDef = EntityDef.fromJson(oldEntityJson);
EntityDef newDef = EntityDef.fromJson(newEntityJson);

// 使用相同的接口访问
System.out.println("Old entity health: " + oldDef.components().getHealthMax());
System.out.println("New entity health: " + newDef.components().getHealthMax());
```

### 示例 2：在注册中使用

```java
// EasecationRegistry.java - registerEntities 方法
@Override
public void registerEntities(List<EntityDef> entities) {
    for (EntityDef entityDef : entities) {
        String identifier = entityDef.id();

        // 1. 注册组件到 EntityDataDriven（现在是版本无关的）
        EntityDataDriven.registerComponents(identifier, entityDef.components());

        // 2. 注册实体工厂
        Entity.registerEntity(
            identifier,
            identifier,
            EntityDataDriven.class,
            (chunk, nbt) -> {
                if (!nbt.contains("identifier")) {
                    nbt.putString("identifier", identifier);
                }
                return new EntityDataDriven(chunk, nbt);
            }
        );

        log.debug("[EaseCation] Registered entity: " + identifier +
                  " (version: " + entityDef.version() + ")");
    }
}
```

### 示例 3：EntityDataDriven 中使用

```java
// EntityDataDriven.java - 现在是版本无关的
private void initComponents() {
    if (components == null) {
        return;
    }

    // 使用接口方法（适用于所有版本）
    Double health = components.getHealthMax();
    if (health != null && health > 0) {
        this.setMaxHealth(health.intValue());
    }

    Double speed = components.getMovementSpeed();
    if (speed != null) {
        this.movementSpeed = speed.floatValue();
    }

    // 物理属性
    Boolean hasGravity = components.getPhysicsHasGravity();
    if (hasGravity != null) {
        this.hasGravity = hasGravity;
    }
}
```

## 版本检测机制

EntityLoader 会按以下顺序检测版本：

1. **读取 `format_version` 字段**：从 JSON 根节点提取
2. **解析版本号**：支持 "1.21.60" 或 "1.21" 格式
3. **Fallback 到默认版本**：如果未找到，使用 `FormatVersion.DEFAULT` (1.21.60)

```java
// 自动检测示例
{
  "format_version": "1.19.40",  // ← 检测到 1.19.40
  "minecraft:entity": { ... }
}

// 如果没有 format_version
{
  "minecraft:entity": { ... }  // ← 使用默认 1.21.60
}
```

## 添加新版本支持

### 步骤 1：生成新版本 DTO

```bash
# 切换到对应版本的 schema commit
cd schemas/minecraft-bedrock-json-schemas
git checkout <commit-id-for-v1.22.0>

# 生成 DTO
cd ../../codegen
npm run generate -- --mc-version 1.22.0
```

### 步骤 2：创建组件适配器

```java
// EntityComponentsV1_22_0.java
package net.easecation.bridge.core.versioned.entity;

import net.easecation.bridge.core.dto.entity.v1_22_0.Components;
import net.easecation.bridge.core.versioned.FormatVersion;
import net.easecation.bridge.core.versioned.IEntityComponents;

public class EntityComponentsV1_22_0 implements IEntityComponents {
    private final Components components;

    public EntityComponentsV1_22_0(Components components) {
        this.components = components;
    }

    @Override
    public FormatVersion getVersion() {
        return new FormatVersion(1, 22, 0);
    }

    @Override
    public Double getHealthMax() {
        if (components.minecraft_health() == null) {
            return null;
        }
        return components.minecraft_health().max() != null
            ? components.minecraft_health().max().doubleValue()
            : null;
    }

    // ... 实现其他接口方法
}
```

### 步骤 3：在 FormatVersion 中添加常量

```java
// FormatVersion.java
public static final FormatVersion V1_22_0 = new FormatVersion(1, 22, 0);
```

### 步骤 4：测试

```java
// 测试新版本加载
String json = """
{
  "format_version": "1.22.0",
  "minecraft:entity": { ... }
}
""";

EntityDef def = EntityDef.fromJson(json);
assert def.version().equals(FormatVersion.V1_22_0);
```

## 迁移旧代码

### 迁移前（硬编码 v1_21_60）

```java
import net.easecation.bridge.core.dto.entity.v1_21_60.Components;
import net.easecation.bridge.core.dto.entity.v1_21_60.Range_a_B_;

public void initComponents(Components components) {
    if (components.minecraft_health() != null) {
        var health = components.minecraft_health();
        if (health.max() != null) {
            this.setMaxHealth(health.max());
        }
    }
}
```

### 迁移后（版本无关）

```java
import net.easecation.bridge.core.versioned.IEntityComponents;

public void initComponents(IEntityComponents components) {
    Double healthMax = components.getHealthMax();
    if (healthMax != null) {
        this.setMaxHealth(healthMax.intValue());
    }
}
```

## 常见问题

### Q: 如何访问版本特定的字段？

A: 使用 `getRawComponents()` 获取原始 DTO 对象，然后进行类型转换：

```java
IEntityComponents components = entityDef.components();
Object raw = components.getRawComponents();

if (components.getVersion().equals(FormatVersion.V1_21_60)) {
    net.easecation.bridge.core.dto.entity.v1_21_60.Components v1_21_60 =
        (net.easecation.bridge.core.dto.entity.v1_21_60.Components) raw;

    // 访问版本特定字段
    var customComponent = v1_21_60.minecraft_someNewComponent();
}
```

### Q: 如何处理不支持的版本？

A: EntityLoader 会抛出 IOException：

```java
try {
    EntityDef def = EntityDef.fromJson(json);
} catch (IOException e) {
    if (e.getMessage().contains("Unsupported entity version")) {
        log.error("This version is not yet supported. Please generate DTO first.");
    }
}
```

### Q: 性能影响如何？

A:
- **首次加载**：使用反射加载类，有轻微开销（~1ms）
- **后续访问**：接口方法调用，性能与直接访问相同
- **内存**：每个实体多持有一个适配器对象（~数百字节）

综合影响可忽略不计。

## 下一步

- [ ] 为 Block 和 Item 创建类似的版本支持系统
- [ ] 添加版本转换/迁移工具
- [ ] 创建版本兼容性测试套件

## 参考

- [codegen/README.md](../codegen/README.md) - DTO 代码生成文档
- [addon-bridge-core/src/main/java/net/easecation/bridge/core/versioned/README.md](../addon-bridge-core/src/main/java/net/easecation/bridge/core/versioned/README.md) - 版本系统架构
