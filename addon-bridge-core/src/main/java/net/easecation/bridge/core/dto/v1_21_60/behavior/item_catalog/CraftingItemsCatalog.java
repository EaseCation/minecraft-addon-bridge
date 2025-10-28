package net.easecation.bridge.core.dto.v1_21_60.behavior.item_catalog;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CraftingItemsCatalog(
    @JsonProperty("categories") List<Category> categories
) {
}
