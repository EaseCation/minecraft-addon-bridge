package net.easecation.bridge.adapter.pm1e.item;

import cn.nukkit.item.custom.ItemDefinition;
import net.easecation.bridge.core.ItemDef;

/**
 * Builder for PM1E ItemDefinition from ItemDef.
 */
public class ItemDefinitionBuilder {

    public static ItemDefinition build(
        Class<? extends ItemDataDriven> itemClass,
        ItemDef itemDef,
        int nukkitId,
        String textureName
    ) {
        String identifier = itemDef.id();

        var builder = ItemDefinition.builder()
            .identifier(identifier)
            .legacyId(nukkitId)
            .implementation(itemClass);

        // PM1E requires non-null texture field
        // For Legacy mode, use identifier as texture since client handles rendering
        builder.texture(textureName != null ? textureName : itemDef.id().replace(":", "_"));

        boolean foil = extractFoil(itemDef);
        if (foil) {
            builder.foil(true);
        }

        ItemDefinition.CreativeCategory category = extractCreativeCategory(itemDef);
        if (category != null) {
            builder.creativeCategory(category);
        }

        String group = extractCreativeGroup(itemDef);
        if (group != null) {
            builder.creativeGroup(group);
        }

        return builder.build();
    }

    private static boolean extractFoil(ItemDef itemDef) {
        if (itemDef.isLegacy()) {
            // Legacy mode uses foil field
            var components = itemDef.legacyComponents();
            if (components != null && components.getFoil() != null) {
                return components.getFoil();
            }
            return false;
        }

        // Component-based mode: Record accessor method
        if (itemDef.componentComponents() != null
            && itemDef.componentComponents().minecraft_glint() != null) {
            var glint = itemDef.componentComponents().minecraft_glint();

            // Glint is a sealed interface with two variants
            if (glint instanceof net.easecation.bridge.core.dto.item.v1_21_60.Glint.Glint_Variant0 variant0) {
                return variant0.value() != null && variant0.value();
            } else if (glint instanceof net.easecation.bridge.core.dto.item.v1_21_60.Glint.Glint_Variant1 variant1) {
                return variant1.value() != null && variant1.value();
            }
        }

        return false;
    }

    private static ItemDefinition.CreativeCategory extractCreativeCategory(ItemDef itemDef) {
        String categoryStr = null;

        if (itemDef.isLegacy()) {
            // Legacy mode: category is directly in description
            var description = itemDef.legacyDescription();
            if (description != null) {
                categoryStr = description.category();
            }
        }

        if (categoryStr == null) {
            return null;
        }

        return switch (categoryStr.toLowerCase()) {
            case "construction" -> ItemDefinition.CreativeCategory.CONSTRUCTION;
            case "equipment" -> ItemDefinition.CreativeCategory.EQUIPMENT;
            case "items" -> ItemDefinition.CreativeCategory.ITEMS;
            case "nature" -> ItemDefinition.CreativeCategory.NATURE;
            default -> null;
        };
    }

    private static String extractCreativeGroup(ItemDef itemDef) {
        // Legacy mode does not have group concept, return null
        // Group is only available in component-based mode
        return null;
    }
}
