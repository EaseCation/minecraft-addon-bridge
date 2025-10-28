package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Can only be used by Villagers. Allows the mob to accept flowers from Iron Golems. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TakeFlower(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Conditions that need to be met for the behavior to start. */
    @JsonProperty("filters") @Nullable Filters filters,
    /* Maximum rotation (in degrees), on the Y-axis, this entity can rotate its head while trying to look at the target. */
    @JsonProperty("max_head_rotation_y") @Nullable Double maxHeadRotationY,
    /* Maximum rotation (in degrees), on the X-axis, this entity can rotate while trying to look at the target. */
    @JsonProperty("max_rotation_x") @Nullable Double maxRotationX,
    /* The maximum amount of time (in seconds) for the mob to randomly wait for before taking the flower. */
    @JsonProperty("max_wait_time") @Nullable Double maxWaitTime,
    /* Minimum distance (in blocks) for the entity to be considered having reached its target. */
    @JsonProperty("min_distance_to_target") @Nullable Double minDistanceToTarget,
    /* The minimum amount of time (in seconds) for the mob to randomly wait for before taking the flower. */
    @JsonProperty("min_wait_time") @Nullable Double minWaitTime,
    /* The dimensions of the AABB used to search for a potential mob to take a flower from. */
    @JsonProperty("search_area") @Nullable VectorOf3Items searchArea
) {
}
