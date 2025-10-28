package net.easecation.bridge.core.dto.v1_21_60.behavior.item_catalog;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* You can add new items to the existing categories. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Category(
    /* Determines which category this block/item will be placed under in the inventory and crafting table container screens. */
    @JsonProperty("category_name") String categoryName,
    /* Defines a new group which will be added to the category you specify at the end of all existing items/groups. */
    @JsonProperty("groups") List<Group> groups
) {
}
