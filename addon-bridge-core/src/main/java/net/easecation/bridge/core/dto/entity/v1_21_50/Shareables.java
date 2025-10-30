package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines a list of items the mob wants to share or pick up. Each item must have the following parameters: */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Shareables(
    /* A bucket for all other items in the game. Note this category is always least priority items. */
    @JsonProperty("all_items") @Nullable Boolean allItems,
    /* Maximum number of this item the mob will hold. */
    @JsonProperty("all_items_max_amount") @Nullable Integer allItemsMaxAmount,
    /* Number of this item considered extra that the entity wants to share. */
    @JsonProperty("all_items_surplus_amount") @Nullable Integer allItemsSurplusAmount,
    /* Number of this item this entity wants to share. */
    @JsonProperty("all_items_want_amount") @Nullable Integer allItemsWantAmount,
    /* List of items that the entity wants to share. */
    @JsonProperty("items") @Nullable List<Object> items,
    /* Determines whether the mob can only pickup one item at a time. */
    @JsonProperty("singular_pickup") @Nullable Boolean singularPickup
) {
}
