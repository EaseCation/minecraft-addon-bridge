package net.easecation.bridge.core.dto.v1_21_60.behavior.item_catalog;

import com.fasterxml.jackson.annotation.*;

/* Used to define the creative inventory/recipe book */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CraftingItemCatalogDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:crafting_items_catalog") CraftingItemsCatalog minecraft_craftingItemsCatalog
) {
}
