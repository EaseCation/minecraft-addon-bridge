package net.easecation.bridge.core;

import javax.annotation.Nullable;
import java.util.Map;

public record ItemDef(String id, Map<String, Object> components, @Nullable MenuCategoryInfo menuCategory) {

    /**
     * Menu category information for creative mode inventory.
     */
    public record MenuCategoryInfo(
        @Nullable String category,      // CONSTRUCTION, EQUIPMENT, ITEMS, NATURE, NONE
        @Nullable String group,
        @Nullable Boolean isHiddenInCommands
    ) {}
}

