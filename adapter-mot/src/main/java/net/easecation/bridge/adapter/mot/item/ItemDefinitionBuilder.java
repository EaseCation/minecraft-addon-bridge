package net.easecation.bridge.adapter.mot.item;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustom;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;
import net.easecation.bridge.core.ItemDef;

import java.util.Map;

/**
 * Builder utility to convert ItemDef components to MOT's CustomItemDefinition.
 * Uses CustomItemDefinition's builder pattern to construct item definitions.
 */
public class ItemDefinitionBuilder {

    /**
     * Build a CustomItemDefinition from ItemDef components.
     * @param item The ItemCustom instance
     * @param itemDef The ItemDef containing components
     * @return Configured CustomItemDefinition
     */
    public static CustomItemDefinition build(ItemCustom item, ItemDef itemDef) {
        Map<String, Object> components = itemDef.components();

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
    private static ItemType determineItemType(Map<String, Object> components) {
        // For now, always use SIMPLE builder as it's the most compatible
        // MOT's specialized builders may have different APIs than expected
        return ItemType.SIMPLE;
    }

    /**
     * Build a simple item definition.
     */
    private static CustomItemDefinition buildSimple(ItemCustom item, Map<String, Object> components) {
        ItemCreativeCategory category = extractCreativeCategory(components);
        CustomItemDefinition.SimpleBuilder builder = CustomItemDefinition.simpleBuilder(item, category);

        // Apply common properties
        applyCommonProperties(builder, components);

        return builder.build();
    }

    /**
     * Build a food item definition.
     */
    private static CustomItemDefinition buildFood(ItemCustom item, Map<String, Object> components) {
        // Use simple builder for food items as well
        return buildSimple(item, components);
    }

    /**
     * Build a tool item definition.
     */
    private static CustomItemDefinition buildTool(ItemCustom item, Map<String, Object> components) {
        // Use simple builder for tool items as well
        return buildSimple(item, components);
    }

    /**
     * Build an armor item definition.
     */
    private static CustomItemDefinition buildArmor(ItemCustom item, Map<String, Object> components) {
        // Use simple builder for armor items as well
        return buildSimple(item, components);
    }

    /**
     * Apply common properties that work across all builder types.
     */
    private static void applyCommonProperties(CustomItemDefinition.SimpleBuilder builder, Map<String, Object> components) {
        // Allow off-hand
        Object allowOffHand = components.get("minecraft:allow_off_hand");
        if (allowOffHand instanceof Boolean) {
            builder.allowOffHand((Boolean) allowOffHand);
        }

        // Hand equipped
        Object handEquipped = components.get("minecraft:hand_equipped");
        if (handEquipped instanceof Boolean) {
            builder.handEquipped((Boolean) handEquipped);
        }

        // Glint (foil effect)
        Object glint = components.get("minecraft:glint");
        if (glint instanceof Boolean) {
            builder.foil((Boolean) glint);
        }

        // Creative group
        Object creativeGroup = components.get("minecraft:creative_group");
        if (creativeGroup instanceof String) {
            builder.creativeGroup((String) creativeGroup);
        }

        // Can destroy in creative
        Object canDestroyInCreative = components.get("minecraft:can_destroy_in_creative");
        if (canDestroyInCreative instanceof Boolean) {
            builder.canDestroyInCreative((Boolean) canDestroyInCreative);
        }

        // Max stack size (handled in ItemDataDriven.getMaxStackSize())
        // Durability (handled in specific builders or ItemDataDriven.getMaxDurability())
    }

    /**
     * Extract creative category from components.
     */
    private static ItemCreativeCategory extractCreativeCategory(Map<String, Object> components) {
        Object categoryObj = components.get("minecraft:creative_category");
        if (categoryObj instanceof String) {
            String category = (String) categoryObj;
            // Map string to enum - use available MOT categories
            switch (category.toLowerCase()) {
                case "equipment":
                    return ItemCreativeCategory.EQUIPMENT;
                case "nature":
                    return ItemCreativeCategory.NATURE;
                case "items":
                default:
                    return ItemCreativeCategory.ITEMS;
            }
        }
        return ItemCreativeCategory.ITEMS;
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
