package net.easecation.bridge.adapter.pnx.mapper;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.custom.CustomEntityDefinition;
import net.easecation.bridge.core.EntityDef;

/**
 * 从EntityDef构建CustomEntityDefinition
 *
 * 注意：Entity有375个组件，这里仅实现核心组件（约8个），其他组件标记为TODO
 */
public class EntityDefinitionBuilder {

    public EntityDefinitionBuilder() {
    }

    /**
     * 从EntityDef构建CustomEntityDefinition
     * 注意：entityInstance 参数保留用于未来扩展，当前未使用
     */
    public CustomEntityDefinition build(EntityDef entityDef, Entity entityInstance) {
        MappingContext context = new MappingContext(entityDef.id());

        // simpleBuilder 参数是字符串 ID
        var builder = CustomEntityDefinition.simpleBuilder(entityDef.id());

        // 应用Description
        if (entityDef.description() != null) {
            // 设置 eid (继承的原版实体ID)，如果没有指定则默认继承 pig
            String eid = "minecraft:pig"; // 默认继承猪的属性
            builder.eid(eid);

            if (entityDef.description().isSpawnable() != null && entityDef.description().isSpawnable()) {
                builder.hasSpawnEgg(true);
            }
            if (entityDef.description().isSummonable() != null) {
                builder.isSummonable(entityDef.description().isSummonable());
            }
        } else {
            // 没有 description 时也设置默认 eid
            builder.eid("minecraft:pig");
        }

        // 应用核心组件（注意：Components是class，使用字段访问）
        if (entityDef.components() != null) {
            var components = entityDef.components();

            // minecraft:health
            if (components.minecraft_health != null && components.minecraft_health.value() != null) {
                var healthValue = extractRangeValue(components.minecraft_health.value());
                if (healthValue != null) {
                    builder.maxHealth(healthValue.intValue());
                }
            }

            // minecraft:movement
            if (components.minecraft_movement != null && components.minecraft_movement.value() != null) {
                var speedValue = extractRangeValue(components.minecraft_movement.value());
                if (speedValue != null) {
                    builder.movement(speedValue.floatValue(), 7.0f);
                }
            }

            // minecraft:attack
            if (components.minecraft_attack != null && components.minecraft_attack.damage() != null) {
                var damageValue = extractRangeValue(components.minecraft_attack.damage());
                if (damageValue != null) {
                    builder.attack(damageValue.intValue());
                }
            }

            // minecraft:collision_box
            if (components.minecraft_collisionBox != null) {
                var collisionBox = components.minecraft_collisionBox;
                if (collisionBox.width() != null && collisionBox.height() != null) {
                    builder.collisionBox(collisionBox.width().floatValue(), collisionBox.height().floatValue());
                }
            }

            // minecraft:type_family
            if (components.minecraft_typeFamily != null && components.minecraft_typeFamily.family() != null) {
                String[] families = components.minecraft_typeFamily.family().toArray(new String[0]);
                builder.typeFamily(families);
            }

            // minecraft:knockback_resistance
            if (components.minecraft_knockbackResistance != null && components.minecraft_knockbackResistance.value() != null) {
                var resValue = extractRangeValue(components.minecraft_knockbackResistance.value());
                if (resValue != null) {
                    builder.knockbackResistance(resValue.floatValue());
                }
            }

            // minecraft:is_persistent (注意字段名可能是 minecraft_persistent)
            // 暂时注释掉，因为需要确认实际的字段名
            // if (components.minecraft_isPersistent != null) {
            //     builder.isPersistent(true);
            // }

            // 约340+个组件未映射（包括scale, nameable, loot, equipment, inventory等）
            context.warn("Entity has 340+ components not yet mapped. Only core components (7) are currently implemented.");
        }

        // 报告未映射的组件
        if (context.hasUnmappedComponents()) {
            context.info("Entity has " + context.getUnmappedComponents().size() + " unmapped components");
        }

        return builder.build();
    }

    /**
     * 从 Range_a_B 类型中提取数值
     * Range_a_B 可以是单个值、值列表或范围对象（min/max）
     */
    private Double extractRangeValue(net.easecation.bridge.core.dto.entity.v1_21_60.Range_a_B range) {
        if (range instanceof net.easecation.bridge.core.dto.entity.v1_21_60.Range_a_B.Range_a_B_Variant0 variant0) {
            // 单个值
            return variant0.value();
        } else if (range instanceof net.easecation.bridge.core.dto.entity.v1_21_60.Range_a_B.Range_a_B_Variant1 variant1) {
            // 值列表，取第一个值
            if (variant1.value() != null && !variant1.value().isEmpty()) {
                return variant1.value().get(0);
            }
        } else if (range instanceof net.easecation.bridge.core.dto.entity.v1_21_60.Range_a_B.Range_a_B_Variant2 variant2) {
            // 范围对象，取最小值（或平均值）
            if (variant2.rangeMin() != null) {
                return variant2.rangeMin();
            } else if (variant2.rangeMax() != null) {
                return variant2.rangeMax();
            }
        }
        return null;
    }
}
