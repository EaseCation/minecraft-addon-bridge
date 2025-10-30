package net.easecation.bridge.adapter.mot.item;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustom;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;
import net.easecation.bridge.core.ItemDef;
import net.easecation.bridge.core.dto.item.v1_21_60.Item;

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
        Item.Components components = itemDef.components();

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
