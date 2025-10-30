package net.easecation.bridge.core;

import net.easecation.bridge.core.dto.item.v1_21_60.Item;

import javax.annotation.Nullable;

/**
 * Represents a custom item definition parsed from behavior pack.
 * Uses the LATEST version (v1_21_60) - old versions will be auto-upgraded in the future.
 */
public record ItemDef(
        String id,
        @Nullable Item.Description description,
        @Nullable Item.Components components
) {
    // Constructor from DTO (always uses latest version)
    public static ItemDef fromDTO(Item dto) {
        String identifier = dto.description() != null && dto.description().identifier() != null
                ? dto.description().identifier()
                : "unknown:item";

        return new ItemDef(
                identifier,
                dto.description(),
                dto.components()
        );
    }

    /**
     * Get menu category from description.
     */
    @Nullable
    public Item.Description.MenuCategory menuCategory() {
        return description != null ? description.menuCategory() : null;
    }
}

