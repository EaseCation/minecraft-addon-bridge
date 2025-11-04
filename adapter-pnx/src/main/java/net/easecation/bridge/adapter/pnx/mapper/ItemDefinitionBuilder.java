package net.easecation.bridge.adapter.pnx.mapper;

import cn.nukkit.item.Item;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.nbt.tag.CompoundTag;
import net.easecation.bridge.core.BridgeLoggerHolder;
import net.easecation.bridge.core.ItemDef;

/**
 * 从ItemDef构建CustomItemDefinition（支持Component-based和Legacy模式）
 */
public class ItemDefinitionBuilder {

    public ItemDefinitionBuilder() {
    }

    /**
     * 从ItemDef构建CustomItemDefinition
     * 支持Component-based模式和Legacy模式
     * @param itemInstance 必须同时继承 Item 和实现 CustomItem 接口
     */
    public <T extends Item & cn.nukkit.item.customitem.CustomItem> CustomItemDefinition build(ItemDef itemDef, T itemInstance) {
        // 检查是否为Legacy模式
        if (itemDef.registrationMode() == ItemDef.ItemRegistrationMode.LEGACY) {
            return buildLegacyDefinition(itemDef);
        }

        MappingContext context = new MappingContext(itemDef.id());

        // 获取builder（itemInstance 现在是正确的类型）
        var builder = CustomItemDefinition.simpleBuilder(itemInstance);

        // 应用Description - texture
        if (itemDef.componentDescription() != null && itemDef.componentDescription().identifier() != null) {
            String identifier = itemDef.componentDescription().identifier();
            String textureName = identifier.contains(":") ?
                identifier.substring(identifier.indexOf(':') + 1) : identifier;
            builder.texture(textureName);
        }

        // 应用组件
        if (itemDef.componentComponents() != null) {
            var components = itemDef.componentComponents();

            // minecraft:display_name
            if (components.minecraft_displayName() != null) {
                builder.name(components.minecraft_displayName().value());
            }

            // minecraft:max_stack_size
            if (components.minecraft_maxStackSize() != null) {
                var maxStackSize = components.minecraft_maxStackSize();
                if (maxStackSize instanceof net.easecation.bridge.core.dto.item.v1_21_60.MaxStackSize.MaxStackSize_Variant0 variant0) {
                    builder.maxStackSize(variant0.value().intValue());
                } else if (maxStackSize instanceof net.easecation.bridge.core.dto.item.v1_21_60.MaxStackSize.MaxStackSize_Variant1 variant1) {
                    if (variant1.value() != null) {
                        builder.maxStackSize(variant1.value().intValue());
                    }
                }
            }

            // minecraft:icon
            // TODO: Icon组件的结构可能不同，暂时跳过
            // if (components.minecraft_icon() != null) {
            //     context.recordUnmappedComponent("minecraft:icon", "Icon structure mapping needed");
            // }

            // minecraft:hand_equipped
            if (components.minecraft_handEquipped() != null) {
                builder.handEquipped(Boolean.TRUE.equals(components.minecraft_handEquipped()));
            }

            // minecraft:allow_off_hand
            if (components.minecraft_allowOffHand() != null) {
                builder.allowOffHand(Boolean.TRUE.equals(components.minecraft_allowOffHand()));
            }

            // minecraft:glint
            if (components.minecraft_glint() != null) {
                builder.glint(Boolean.TRUE.equals(components.minecraft_glint()));
            }

            // minecraft:durability
            if (components.minecraft_durability() != null) {
                var durability = components.minecraft_durability();
                Integer maxDurability = durability.maxDurability();
                if (maxDurability != null) {
                    builder.durability(maxDurability, 5, 30); // 默认值
                }
            }

            // minecraft:damage
            if (components.minecraft_damage() != null) {
                var damage = components.minecraft_damage();
                if (damage instanceof net.easecation.bridge.core.dto.item.v1_21_60.Damage.Damage_Variant0 variant0) {
                    builder.damage(variant0.value().intValue());
                } else if (damage instanceof net.easecation.bridge.core.dto.item.v1_21_60.Damage.Damage_Variant1 variant1) {
                    if (variant1.value() != null) {
                        builder.damage(variant1.value().intValue());
                    }
                }
            }

            // minecraft:enchantable
            if (components.minecraft_enchantable() != null && components.minecraft_enchantable().value() != null) {
                builder.enchantable(cn.nukkit.item.utils.ItemEnchantSlot.SWORD, components.minecraft_enchantable().value().intValue());
            }

            // minecraft:fuel
            if (components.minecraft_fuel() != null && components.minecraft_fuel().duration() != null) {
                builder.fuel(components.minecraft_fuel().duration().floatValue());
            }

            // minecraft:cooldown
            if (components.minecraft_cooldown() != null) {
                var cooldown = components.minecraft_cooldown();
                String category = cooldown.category() != null ? cooldown.category() : "default";
                float duration = cooldown.duration() != null ? cooldown.duration().floatValue() : 0.0f;
                if (duration > 0) {
                    builder.cooldown(category, duration);
                }
            }

            // minecraft:block_placer
            if (components.minecraft_blockPlacer() != null) {
                var blockPlacer = components.minecraft_blockPlacer();
                if (blockPlacer.block() != null) {
                    String blockId = blockPlacer.block();
                    if (blockPlacer.useOn() != null && !blockPlacer.useOn().isEmpty()) {
                        // 有限制的可放置方块列表
                        builder.blockPlacer(blockId, blockPlacer.useOn().toArray(new String[0]));
                    } else {
                        // 无限制
                        builder.blockPlacer(blockId);
                    }
                }
            }

            // minecraft:food
            if (components.minecraft_food() != null) {
                var food = components.minecraft_food();
                boolean canAlwaysEat = food.canAlwaysEat() != null && food.canAlwaysEat();
                // nutrition 是 Double，需要转换为 int
                int nutrition = food.nutrition() != null ? food.nutrition().intValue() : 0;
                float saturation = food.saturationModifier() != null ? food.saturationModifier().floatValue() : 0.0f;
                // Food 组件可能没有 convertsTo 字段，暂时传 null
                builder.food(canAlwaysEat, nutrition, saturation, null);
            }

            // minecraft:wearable
            if (components.minecraft_wearable() != null) {
                var wearable = components.minecraft_wearable();
                if (wearable.slot() != null) {
                    String slot = wearable.slot();
                    int protection = wearable.protection() != null ? wearable.protection() : 0;
                    builder.wearable(slot, protection, false); // displaceArmor 默认 false
                }
            }

            // 记录未映射的组件
            if (components.minecraft_digger() != null) {
                context.recordUnmappedComponent("minecraft:digger", "Complex component, requires DiggerEntry construction");
            }
            if (components.minecraft_entityPlacer() != null) {
                context.recordUnmappedComponent("minecraft:entity_placer", "Not yet implemented");
            }
            if (components.minecraft_projectile() != null) {
                context.recordUnmappedComponent("minecraft:projectile", "Not yet implemented");
            }
            if (components.minecraft_throwable() != null) {
                context.recordUnmappedComponent("minecraft:throwable", "Complex component, requires detailed parameter mapping");
            }
        }

        // 报告未映射的组件
        if (context.hasUnmappedComponents()) {
            context.info("Item has " + context.getUnmappedComponents().size() + " unmapped components");
        }

        return builder.build();
    }

    /**
     * 为Legacy模式物品构建CustomItemDefinition
     * 使用PnxLegacyItemNBTBuilder生成NBT数据
     */
    private CustomItemDefinition buildLegacyDefinition(ItemDef itemDef) {
        BridgeLoggerHolder.getLogger().info("[ItemDefinitionBuilder] Building Legacy mode definition for: " + itemDef.id());

        // 使用PnxLegacyItemNBTBuilder生成NBT
        CompoundTag nbt = PnxLegacyItemNBTBuilder.buildNBT(itemDef);

        // 创建CustomItemDefinition
        // Legacy模式可能返回null NBT（简单物品），这是正常的
        CustomItemDefinition definition = new CustomItemDefinition(itemDef.id(), nbt);

        BridgeLoggerHolder.getLogger().info("[ItemDefinitionBuilder] Created Legacy definition for: " + itemDef.id() +
            " (hasNBT=" + (nbt != null) + ")");

        return definition;
    }
}
