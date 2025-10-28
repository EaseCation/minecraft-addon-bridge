package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to offer the player a flower like the Iron Golem does. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OfferFlower(
    @JsonProperty("priority") @Nullable Priority priority,
    /* Percent chance that the mob will start this goal from 0.0 to 1.0 (where 1.0 = 100%). */
    @JsonProperty("chance_to_start") @Nullable Double chanceToStart,
    /* Conditions that need to be met for the behavior to start. */
    @JsonProperty("filters") @Nullable Filters filters,
    /* Maximum rotation (in degrees), on the Y-axis, this entity can rotate its head while trying to look at the target. */
    @JsonProperty("max_head_rotation_y") @Nullable Double maxHeadRotationY,
    /* The max amount of time (in seconds) that the mob will offer the flower for before exiting the Goal. */
    @JsonProperty("max_offer_flower_duration") @Nullable Double maxOfferFlowerDuration,
    /* Maximum rotation (in degrees), on the X-axis, this entity can rotate while trying to look at the target. */
    @JsonProperty("max_rotation_x") @Nullable Double maxRotationX,
    /* The dimensions of the AABB used to search for a potential mob to offer flower to. */
    @JsonProperty("search_area") @Nullable VectorOf3Items searchArea
) {
}
