package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Adds a timer for the entity to grow up. It can be accelerated by giving the entity the items it likes as defined by feedItems. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Ageable(
    /* List of items that the entity drops when it grows up. */
    @JsonProperty("drop_items") @Nullable Object dropItems,
    /* Amount of time before the entity grows up, -1 for always a baby. */
    @JsonProperty("duration") @Nullable Double duration,
    /* List of items that can be fed to the entity. Includes {@code item} for the item name and {@code growth} to define how much time it grows up by */
    @JsonProperty("feed_items") @Nullable Object feedItems,
    /* Event to run when this entity grows up. */
    @JsonProperty("grow_up") @Nullable Event growUp,
    /* The feed item used will transform to this item upon successful interaction. Format: itemName:auxValue */
    @JsonProperty("transform_to_item") @Nullable EntityBb transformToItem,
    /* List of conditions to meet so that the entity can be fed. */
    @JsonProperty("interact_filters") @Nullable Filters interactFilters
) {
}
