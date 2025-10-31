package net.easecation.bridge.adapter.mot.item;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustom;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;
import net.easecation.bridge.core.ItemDef;
import net.easecation.bridge.core.dto.item.v1_21_60.Item;

/**
 * Builder utility to convert ItemDef components to MOT's CustomItemDefinition.
 * Uses CustomItemDefinition's builder pattern to construct item definitions.
 * Supports both Legacy (v1.10) and Component-based (v1.19+) registration modes.
 */
public class ItemDefinitionBuilder {

    /**
     * Build a CustomItemDefinition from ItemDef components.
     * Automatically detects registration mode and uses appropriate builder.
     *
     * @param item The ItemCustom instance
     * @param itemDef The ItemDef containing components
     * @return Configured CustomItemDefinition
     */
    public static CustomItemDefinition build(ItemCustom item, ItemDef itemDef) {
        // 根据registrationMode分支处理
        if (itemDef.isLegacy()) {
            return buildLegacy(item, itemDef);
        } else {
            return buildComponentBased(item, itemDef);
        }
    }

    /**
     * Build a Legacy mode (v1.10) item definition.
     * Uses legacyBuilder or legacyFoodBuilder based on components.
     */
    private static CustomItemDefinition buildLegacy(ItemCustom item, ItemDef itemDef) {
        net.easecation.bridge.core.dto.item.v1_10.Components legacyComponents = itemDef.legacyComponents();

        // 判断是否为食物
        if (legacyComponents != null && legacyComponents.getFood() != null) {
            return buildLegacyFood(item, legacyComponents);
        } else {
            return buildLegacyItem(item, legacyComponents);
        }
    }

    /**
     * Build a Legacy mode simple item.
     * Currently implements basic components only, complex components marked as TODO.
     */
    private static CustomItemDefinition buildLegacyItem(ItemCustom item,
            net.easecation.bridge.core.dto.item.v1_10.Components components) {

        CustomItemDefinition.LegacyItemBuilder builder = CustomItemDefinition.legacyBuilder(item);

        if (components != null) {
            // 基础组件：max_stack_size
            if (components.getMaxStackSize() != null && components.getMaxStackSize().value() != null) {
                builder.maxStackSize(components.getMaxStackSize().value());
            }

            // TODO: minecraft:fuel（燃料属性）
            // TODO: minecraft:foil（附魔光效）
            // TODO: minecraft:hand_equipped（手持渲染）
            // TODO: minecraft:use_animation（使用动画）
            // TODO: minecraft:seed（种子属性）
            // TODO: netease:allow_offhand（副手支持）
            // TODO: netease:fire_resistant（防火）
            // TODO: netease:customtips（自定义提示）
            // TODO: netease:armor（盔甲属性）
            // TODO: netease:weapon（武器属性）
            // TODO: netease:bucket（桶属性）
            // TODO: additionalComponents（其他未知组件）
        }

        return builder.build();
    }

    /**
     * Build a Legacy mode food item.
     * Implements basic food properties, effects and cooldown marked as TODO.
     */
    private static CustomItemDefinition buildLegacyFood(ItemCustom item,
            net.easecation.bridge.core.dto.item.v1_10.Components components) {

        CustomItemDefinition.LegacyFoodBuilder builder = CustomItemDefinition.legacyFoodBuilder(item);

        var food = components.getFood();
        if (food != null) {
            // 食物基础属性
            int nutrition = food.nutrition() != null ? food.nutrition() : 0;
            float saturation = parseSaturationModifier(food.saturationModifier());
            boolean canAlwaysEat = food.canAlwaysEat() != null && food.canAlwaysEat();

            builder.foodProperties(nutrition, saturation, canAlwaysEat);

            // using_converts_to（食用后转换物品）
            if (food.usingConvertsTo() != null) {
                builder.usingConvertsTo(food.usingConvertsTo());
            }

            // TODO: effects（食物效果列表）
            // TODO: cooldown_type & cooldown_time（冷却时间）
        }

        // use_duration（使用持续时间）
        if (components.getUseDuration() != null) {
            builder.useDuration(components.getUseDuration());
        }

        // TODO: 其他Netease扩展组件

        return builder.build();
    }

    /**
     * Parse saturation modifier string to float value.
     * Maps Bedrock's named values to numeric coefficients.
     */
    private static float parseSaturationModifier(String modifier) {
        if (modifier == null) return 0.6f;
        return switch (modifier.toLowerCase()) {
            case "poor" -> 0.1f;
            case "low" -> 0.3f;
            case "normal" -> 0.6f;
            case "good" -> 0.8f;
            case "max" -> 1.0f;
            case "supernatural" -> 1.2f;
            default -> 0.6f;
        };
    }

    /**
     * Build a Component-based mode (v1.19+) item definition.
     * This is the original build logic, renamed for clarity.
     */
    private static CustomItemDefinition buildComponentBased(ItemCustom item, ItemDef itemDef) {
        Item.Components components = itemDef.componentComponents();

        if (components == null) {
            // Minimal item with default properties
            return CustomItemDefinition
                .simpleBuilder(item, ItemCreativeCategory.ITEMS)
                .build();
        }

        // Determine item type and select appropriate builder
        ItemType itemType = determineItemType(components);

        switch (itemType) {
            case FOOD:
                return buildFood(item, components);
            case TOOL:
                return buildTool(item, components);
            case ARMOR:
                return buildArmor(item, components);
            case SIMPLE:
            default:
                return buildSimple(item, components);
        }
    }

    /**
     * Determine the item type based on components.
     */
    private static ItemType determineItemType(Item.Components components) {
        // For now, always use SIMPLE builder as it's the most compatible
        // MOT's specialized builders may have different APIs than expected
        return ItemType.SIMPLE;
    }

    /**
     * Build a simple item definition.
     */
    private static CustomItemDefinition buildSimple(ItemCustom item, Item.Components components) {
        ItemCreativeCategory category = ItemCreativeCategory.ITEMS; // Default category
        CustomItemDefinition.SimpleBuilder builder = CustomItemDefinition.simpleBuilder(item, category);

        // Apply common properties
        applyCommonProperties(builder, components);

        return builder.build();
    }

    /**
     * Build a food item definition.
     */
    private static CustomItemDefinition buildFood(ItemCustom item, Item.Components components) {
        // Use simple builder for food items as well
        return buildSimple(item, components);
    }

    /**
     * Build a tool item definition.
     */
    private static CustomItemDefinition buildTool(ItemCustom item, Item.Components components) {
        // Use simple builder for tool items as well
        return buildSimple(item, components);
    }

    /**
     * Build an armor item definition.
     */
    private static CustomItemDefinition buildArmor(ItemCustom item, Item.Components components) {
        // Use simple builder for armor items as well
        return buildSimple(item, components);
    }

    /**
     * Apply common properties that work across all builder types.
     */
    private static void applyCommonProperties(CustomItemDefinition.SimpleBuilder builder, Item.Components components) {
        // Allow off-hand
        if (components.minecraft_allowOffHand() != null) {
            // AllowOffHand component exists, item can be used off-hand
            builder.allowOffHand(true);
        }

        // Hand equipped
        if (components.minecraft_handEquipped() != null) {
            // HandEquipped component exists, item is hand equipped
            builder.handEquipped(true);
        }

        // Glint (foil effect)
        if (components.minecraft_glint() != null) {
            // Glint component exists, item has foil effect
            builder.foil(true);
        }

        // Can destroy in creative
        if (components.minecraft_canDestroyInCreative() != null) {
            // CanDestroyInCreative component exists
            builder.canDestroyInCreative(true);
        }

        // Max stack size (handled in ItemDataDriven.getMaxStackSize())
        // Durability (handled in specific builders or ItemDataDriven.getMaxDurability())
    }

    /**
     * Item type enum for selecting the appropriate builder.
     */
    private enum ItemType {
        SIMPLE,
        FOOD,
        TOOL,
        ARMOR
    }
}
