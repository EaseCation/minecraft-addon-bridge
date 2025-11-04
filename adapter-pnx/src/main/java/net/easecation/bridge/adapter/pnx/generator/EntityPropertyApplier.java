package net.easecation.bridge.adapter.pnx.generator;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityDataTypes;
import cn.nukkit.entity.data.EntityFlag;
import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.EntityDef;
import net.easecation.bridge.core.dto.entity.v1_21_60.Components;

/**
 * 辅助类：从EntityDef中提取并应用简单属性到实体实例
 *
 * 这个类专注于简单的属性设置，不涉及AI行为系统。
 * 支持的属性包括：scale, nameable, is_baby, can_climb, fire_immune等。
 */
public class EntityPropertyApplier {

    /**
     * 从EntityDef中应用简单属性到实体实例
     * 在实体的initEntity()方法中调用
     *
     * @param entity 实体实例
     * @param entityDef 实体定义
     */
    public static void applyProperties(Entity entity, EntityDef entityDef) {
        if (entityDef == null || entityDef.components() == null) {
            return;
        }

        Components components = entityDef.components();

        try {
            // 1. minecraft:scale - 实体缩放
            applyScale(entity, components);

            // 2. minecraft:nameable - 名称设置
            applyNameable(entity, components);

            // 3. minecraft:is_baby - 幼年状态
            applyIsBaby(entity, components);

            // 4. minecraft:can_climb - 攀爬能力
            applyCanClimb(entity, components);

            // 5. minecraft:can_fly - 飞行能力标志（不涉及飞行AI）
            applyCanFly(entity, components);

            // 6. minecraft:fire_immune - 火焰免疫
            applyFireImmune(entity, components);

            // 7. minecraft:is_shaking - 颤抖状态
            applyIsShaking(entity, components);

            // 8. minecraft:is_charged - 充能状态
            applyIsCharged(entity, components);

            // 9. minecraft:is_saddled - 装鞍状态
            applyIsSaddled(entity, components);

            // 10. minecraft:is_chested - 带箱子状态
            applyIsChested(entity, components);

            // 11. minecraft:interact - 交互文本
            applyInteract(entity, components);

            // ========== 第一批：MOT已实现的组件（9个） ==========

            // 12. minecraft:physics - 重力和碰撞
            applyPhysics(entity, components);

            // 13. minecraft:pushable - 可推动
            applyPushable(entity, components);

            // 14. minecraft:variant - 变体
            applyVariant(entity, components);

            // 15. minecraft:mark_variant - 标记变体
            applyMarkVariant(entity, components);

            // 16. minecraft:skin_id - 皮肤ID
            applySkinId(entity, components);

            // 17. minecraft:collision_box - 运行时碰撞箱调整
            applyCollisionBoxRuntime(entity, components);

            // 18. minecraft:color - 颜色
            applyColor(entity, components);

            // 19. minecraft:color2 - 第二颜色
            applyColor2(entity, components);

            // 20. minecraft:breathable - 呼吸和空气供应
            applyBreathable(entity, components);

            // ========== 第二批：常用EntityFlag组件（12个） ==========

            // 21. minecraft:is_tamed - 驯服状态
            applyIsTamed(entity, components);

            // 22. minecraft:is_sheared - 被剪毛状态
            applyIsSheared(entity, components);

            // 23. minecraft:is_pregnant - 怀孕状态
            applyIsPregnant(entity, components);

            // 24. minecraft:is_stunned - 眩晕状态
            applyIsStunned(entity, components);

            // 25. minecraft:is_illager_captain - 灾厄队长
            applyIsIllagerCaptain(entity, components);

            // 26. minecraft:is_stackable - 可堆叠
            applyIsStackable(entity, components);

            // 27. minecraft:is_hidden_when_invisible - 隐身时隐藏
            applyIsHiddenWhenInvisible(entity, components);

            // 28. minecraft:can_power_jump - 可蓄力跳跃
            applyCanPowerJump(entity, components);

            // 29. minecraft:annotation.break_door - 可破门
            applyAnnotationBreakDoor(entity, components);

            // 30. minecraft:annotation.open_door - 可开门
            applyAnnotationOpenDoor(entity, components);

            // 31. minecraft:angry - 愤怒状态
            applyAngry(entity, components);

            // 32. minecraft:body_rotation_blocked - 身体旋转被阻止
            applyBodyRotationBlocked(entity, components);

            // 33. minecraft:rideable - 可骑乘
            applyRideable(entity, components);

        } catch (Exception e) {
            BridgeLoggerHolder.getLogger().error("[EntityPropertyApplier] Failed to apply properties for entity: " +
                entityDef.id() + " - " + e.getMessage());
        }
    }

    /**
     * 应用minecraft:scale组件
     * 设置实体的视觉大小
     */
    private static void applyScale(Entity entity, Components components) {
        if (components.minecraft_scale != null && components.minecraft_scale.value() != null) {
            float scaleValue = components.minecraft_scale.value().floatValue();
            entity.setScale(scaleValue);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Applied scale: " + scaleValue +
                " to entity: " + entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:nameable组件
     * 设置名称显示相关属性
     */
    private static void applyNameable(Entity entity, Components components) {
        if (components.minecraft_nameable != null) {
            var nameable = components.minecraft_nameable;

            // always_show - 始终显示名称
            if (nameable.alwaysShow() != null && nameable.alwaysShow()) {
                entity.setNameTagAlwaysVisible(true);
                entity.setNameTagVisible(true);
                BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set name tag always visible for: " +
                    entity.getNameTag());
            }

            // allow_name_tag_renaming - 是否允许用名牌重命名
            // 注意：这个属性在PNX中默认支持，无需额外设置
            if (nameable.allowNameTagRenaming() != null && !nameable.allowNameTagRenaming()) {
                // 可以通过设置标志位来禁用重命名，但PNX可能没有直接支持
                // 这里仅记录日志
                BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Name tag renaming disabled for: " +
                    entity.getNameTag());
            }
        }
    }

    /**
     * 应用minecraft:is_baby组件
     * 设置实体为幼年状态（通常会自动缩放为0.5倍）
     */
    private static void applyIsBaby(Entity entity, Components components) {
        if (components.minecraft_isBaby != null) {
            entity.setDataFlag(EntityFlag.BABY, true);
            // 幼年实体通常缩放为0.5倍，但如果已经设置了scale组件，则不覆盖
            if (components.minecraft_scale == null) {
                entity.setScale(0.5f);
            }
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set entity as baby: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:can_climb组件
     * 允许实体攀爬梯子和藤蔓
     */
    private static void applyCanClimb(Entity entity, Components components) {
        if (components.minecraft_canClimb != null) {
            entity.setDataFlag(EntityFlag.CAN_CLIMB, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Enabled climbing for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:can_fly组件
     * 设置飞行能力标志（不涉及飞行AI逻辑）
     */
    private static void applyCanFly(Entity entity, Components components) {
        if (components.minecraft_canFly != null) {
            entity.setDataFlag(EntityFlag.CAN_FLY, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Enabled flying flag for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:fire_immune组件
     * 使实体免疫火焰伤害
     */
    private static void applyFireImmune(Entity entity, Components components) {
        if (components.minecraft_fireImmune != null) {
            // 设置字段（服务器端判定）
            entity.fireProof = true;

            // 设置Flag（客户端同步）
            entity.setDataFlag(EntityFlag.FIRE_IMMUNE, true);

            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set fire immune (field + flag) for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:is_shaking组件
     * 设置实体颤抖状态（如被雷击的苦力怕）
     */
    private static void applyIsShaking(Entity entity, Components components) {
        if (components.minecraft_isShaking != null) {
            entity.setDataFlag(EntityFlag.SHAKING, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set shaking for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:is_charged组件
     * 设置实体充能状态（如被雷击的苦力怕）
     */
    private static void applyIsCharged(Entity entity, Components components) {
        if (components.minecraft_isCharged != null) {
            entity.setDataFlag(EntityFlag.CHARGED, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set charged for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:is_saddled组件
     * 设置实体装鞍状态（如猪）
     */
    private static void applyIsSaddled(Entity entity, Components components) {
        if (components.minecraft_isSaddled != null) {
            entity.setDataFlag(EntityFlag.SADDLED, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set saddled for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:is_chested组件
     * 设置实体带箱子状态（如骡子、驴）
     */
    private static void applyIsChested(Entity entity, Components components) {
        if (components.minecraft_isChested != null) {
            entity.setDataFlag(EntityFlag.CHESTED, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set chested for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:interact组件
     * 设置交互文本（简单支持，不涉及复杂的交互逻辑）
     */
    private static void applyInteract(Entity entity, Components components) {
        if (components.minecraft_interact != null && components.minecraft_interact.interactions() != null) {
            // 设置交互文本数据属性
            // 注意：PNX的INTERACT_TEXT需要字符串
            // 这里我们只是设置一个通用的交互提示
            entity.setDataProperty(EntityDataTypes.INTERACT_TEXT, "右键交互");
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set interact text for: " +
                entity.getNameTag());

            // 实际的交互逻辑需要在实体类中重写onInteract()方法来实现
            // 这里只是设置了交互文本提示
        }
    }

    // ========== 第一批：MOT已实现的组件（9个） ==========

    /**
     * 应用minecraft:physics组件
     * 设置实体的重力和碰撞属性
     */
    private static void applyPhysics(Entity entity, Components components) {
        if (components.minecraft_physics != null) {
            var physics = components.minecraft_physics;

            if (physics.hasGravity() != null) {
                entity.setDataFlag(EntityFlag.HAS_GRAVITY, physics.hasGravity());
                BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set has_gravity: " +
                    physics.hasGravity() + " for: " + entity.getNameTag());
            }

            if (physics.hasCollision() != null) {
                entity.setDataFlag(EntityFlag.HAS_COLLISION, physics.hasCollision());
                BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set has_collision: " +
                    physics.hasCollision() + " for: " + entity.getNameTag());
            }
        }
    }

    /**
     * 应用minecraft:pushable组件
     * 设置实体的可推动属性
     */
    private static void applyPushable(Entity entity, Components components) {
        if (components.minecraft_pushable != null) {
            var pushable = components.minecraft_pushable;

            // 注意：这些需要通过反射设置Entity的protected字段
            try {
                if (pushable.isPushable() != null) {
                    setEntityField(entity, "isPushable", pushable.isPushable());
                    BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set is_pushable: " +
                        pushable.isPushable() + " for: " + entity.getNameTag());
                }
                if (pushable.isPushableByPiston() != null) {
                    setEntityField(entity, "isPushableByPiston", pushable.isPushableByPiston());
                    BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set is_pushable_by_piston: " +
                        pushable.isPushableByPiston() + " for: " + entity.getNameTag());
                }
            } catch (NoSuchFieldException e) {
                // 字段不存在是预期情况（PNX可能通过其他方式管理）
                BridgeLoggerHolder.getLogger().debug("[EntityPropertyApplier] isPushable fields not available in PNX Entity: " +
                    e.getMessage());
            } catch (Exception e) {
                // 其他异常是真实错误
                BridgeLoggerHolder.getLogger().warning("[EntityPropertyApplier] Failed to set pushable: " +
                    e.getMessage());
            }
        }
    }

    /**
     * 应用minecraft:variant组件
     * 设置实体的变体（如不同颜色的羊）
     */
    private static void applyVariant(Entity entity, Components components) {
        if (components.minecraft_variant != null && components.minecraft_variant.value() != null) {
            entity.setDataProperty(EntityDataTypes.VARIANT, components.minecraft_variant.value());
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set variant: " +
                components.minecraft_variant.value() + " for: " + entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:mark_variant组件
     * 设置实体的标记变体（如热带鱼的图案）
     */
    private static void applyMarkVariant(Entity entity, Components components) {
        if (components.minecraft_markVariant != null && components.minecraft_markVariant.value() != null) {
            entity.setDataProperty(EntityDataTypes.MARK_VARIANT, components.minecraft_markVariant.value());
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set mark_variant: " +
                components.minecraft_markVariant.value() + " for: " + entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:skin_id组件
     * 设置实体的皮肤ID（如村民职业）
     */
    private static void applySkinId(Entity entity, Components components) {
        if (components.minecraft_skinId != null && components.minecraft_skinId.value() != null) {
            entity.setDataProperty(EntityDataTypes.SKIN_ID, components.minecraft_skinId.value());
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set skin_id: " +
                components.minecraft_skinId.value() + " for: " + entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:collision_box组件（增强版）
     * 运行时调整碰撞箱大小
     * 注意：PNX已通过Definition设置，这里补充运行时调整
     */
    private static void applyCollisionBoxRuntime(Entity entity, Components components) {
        if (components.minecraft_collisionBox != null) {
            var box = components.minecraft_collisionBox;
            boolean changed = false;

            try {
                if (box.width() != null) {
                    setEntityField(entity, "width", box.width().floatValue());
                    changed = true;
                    BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set width: " +
                        box.width() + " for: " + entity.getNameTag());
                }
                if (box.height() != null) {
                    setEntityField(entity, "height", box.height().floatValue());
                    changed = true;
                    BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set height: " +
                        box.height() + " for: " + entity.getNameTag());
                }

                if (changed) {
                    entity.recalculateBoundingBox();
                }
            } catch (NoSuchFieldException e) {
                // 字段不存在是预期情况（PNX可能通过Definition管理collision box）
                BridgeLoggerHolder.getLogger().debug("[EntityPropertyApplier] width/height fields not available in PNX Entity: " +
                    e.getMessage());
            } catch (Exception e) {
                // 其他异常是真实错误
                BridgeLoggerHolder.getLogger().warning("[EntityPropertyApplier] Failed to set collision box: " +
                    e.getMessage());
            }
        }
    }

    /**
     * 应用minecraft:color组件
     * 设置实体的颜色（如羊毛颜色）
     */
    private static void applyColor(Entity entity, Components components) {
        if (components.minecraft_color != null && components.minecraft_color.value() != null) {
            entity.setDataProperty(EntityDataTypes.COLOR, components.minecraft_color.value().byteValue());
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set color: " +
                components.minecraft_color.value() + " for: " + entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:color2组件
     * 设置实体的第二颜色（如热带鱼）
     */
    private static void applyColor2(Entity entity, Components components) {
        if (components.minecraft_color2 != null && components.minecraft_color2.value() != null) {
            entity.setDataProperty(EntityDataTypes.COLOR_2, components.minecraft_color2.value().byteValue());
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set color2: " +
                components.minecraft_color2.value() + " for: " + entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:breathable组件
     * 设置实体的呼吸和空气供应
     */
    private static void applyBreathable(Entity entity, Components components) {
        if (components.minecraft_breathable != null) {
            var breathable = components.minecraft_breathable;

            if (breathable.totalSupply() != null) {
                entity.setDataProperty(EntityDataTypes.AIR_SUPPLY, breathable.totalSupply().shortValue());
                BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set air_supply: " +
                    breathable.totalSupply() + " for: " + entity.getNameTag());
            }

            entity.setDataFlag(EntityFlag.BREATHING, true);
        }
    }

    // ========== 第二批：常用EntityFlag组件（12个） ==========

    /**
     * 应用minecraft:is_tamed组件
     * 设置实体为驯服状态
     */
    private static void applyIsTamed(Entity entity, Components components) {
        if (components.minecraft_isTamed != null) {
            entity.setDataFlag(EntityFlag.TAMED, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set tamed for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:is_sheared组件
     * 设置实体为被剪毛状态（如羊）
     */
    private static void applyIsSheared(Entity entity, Components components) {
        if (components.minecraft_isSheared != null) {
            entity.setDataFlag(EntityFlag.SHEARED, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set sheared for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:is_pregnant组件
     * 设置实体为怀孕状态
     */
    private static void applyIsPregnant(Entity entity, Components components) {
        if (components.minecraft_isPregnant != null) {
            entity.setDataFlag(EntityFlag.IS_PREGNANT, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set pregnant for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:is_stunned组件
     * 设置实体为眩晕状态
     */
    private static void applyIsStunned(Entity entity, Components components) {
        if (components.minecraft_isStunned != null) {
            entity.setDataFlag(EntityFlag.STUNNED, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set stunned for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:is_illager_captain组件
     * 设置实体为灾厄队长
     */
    private static void applyIsIllagerCaptain(Entity entity, Components components) {
        if (components.minecraft_isIllagerCaptain != null) {
            entity.setDataFlag(EntityFlag.IS_ILLAGER_CAPTAIN, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set illager_captain for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:is_stackable组件
     * 设置实体为可堆叠状态
     */
    private static void applyIsStackable(Entity entity, Components components) {
        if (components.minecraft_isStackable != null) {
            entity.setDataFlag(EntityFlag.STACKABLE, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set stackable for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:is_hidden_when_invisible组件
     * 设置实体在隐身时隐藏
     */
    private static void applyIsHiddenWhenInvisible(Entity entity, Components components) {
        if (components.minecraft_isHiddenWhenInvisible != null) {
            entity.setDataFlag(EntityFlag.HIDDEN_WHEN_INVISIBLE, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set hidden_when_invisible for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:can_power_jump组件
     * 设置实体可蓄力跳跃（如马）
     */
    private static void applyCanPowerJump(Entity entity, Components components) {
        if (components.minecraft_canPowerJump != null) {
            entity.setDataFlag(EntityFlag.CAN_POWER_JUMP, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set can_power_jump for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:annotation.break_door组件
     * 设置实体可破门
     */
    private static void applyAnnotationBreakDoor(Entity entity, Components components) {
        if (components.minecraft_annotationBreakDoor != null) {
            entity.setDataFlag(EntityFlag.DOOR_BREAKER, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set door_breaker for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:annotation.open_door组件
     * 设置实体可开门
     */
    private static void applyAnnotationOpenDoor(Entity entity, Components components) {
        if (components.minecraft_annotationOpenDoor != null) {
            entity.setDataFlag(EntityFlag.DOOR_OPENER, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set door_opener for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:angry组件
     * 设置实体为愤怒状态
     */
    private static void applyAngry(Entity entity, Components components) {
        if (components.minecraft_angry != null) {
            entity.setDataFlag(EntityFlag.ANGRY, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set angry for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:body_rotation_blocked组件
     * 设置实体身体旋转被阻止
     */
    private static void applyBodyRotationBlocked(Entity entity, Components components) {
        if (components.minecraft_bodyRotationBlocked != null) {
            entity.setDataFlag(EntityFlag.BODY_ROTATION_BLOCKED, true);
            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set body_rotation_blocked for: " +
                entity.getNameTag());
        }
    }

    /**
     * 应用minecraft:rideable组件
     * 设置实体可骑乘属性
     */
    private static void applyRideable(Entity entity, Components components) {
        if (components.minecraft_rideable != null) {
            var rideable = components.minecraft_rideable;

            // 设置基本rideable标志
            entity.setDataFlag(EntityFlag.WASD_CONTROLLED, true);  // 可被WASD控制
            entity.setDataFlag(EntityFlag.SADDLED, true);          // 装备鞍

            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set rideable flags (WASD_CONTROLLED, SADDLED)");

            // 如果允许蓄力跳跃
            if (rideable.crouchingSkipInteract() != null && rideable.crouchingSkipInteract()) {
                // 潜行时跳过交互
                BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Rideable: crouching skip interact enabled");
            }

            // 尝试设置座位数量（通过反射，因为PNX Entity可能没有这个字段）
            if (rideable.seatCount() != null && rideable.seatCount() > 0) {
                try {
                    setEntityField(entity, "seatCount", rideable.seatCount());
                    BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set seat count: " +
                        rideable.seatCount());
                } catch (NoSuchFieldException e) {
                    // PNX Entity可能没有seatCount字段，这是正常的
                    BridgeLoggerHolder.getLogger().debug("[EntityPropertyApplier] seatCount field not available (expected in PNX)");
                } catch (Exception e) {
                    BridgeLoggerHolder.getLogger().warning("[EntityPropertyApplier] Failed to set seatCount: " +
                        e.getMessage());
                }
            }

            // 注意：座位位置、旋转设置等详细信息由客户端行为包处理
            // PNX会从CustomEntityDefinition中读取这些信息并传递给客户端
            // seats()返回的是Object类型，其详细结构会在Definition构建时处理

            BridgeLoggerHolder.getLogger().info("[EntityPropertyApplier] Set rideable for: " +
                entity.getNameTag());
        }
    }

    // ========== 辅助方法 ==========

    /**
     * 通过反射设置Entity的protected字段
     */
    private static void setEntityField(Entity entity, String fieldName, Object value) throws Exception {
        java.lang.reflect.Field field = Entity.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(entity, value);
    }
}
