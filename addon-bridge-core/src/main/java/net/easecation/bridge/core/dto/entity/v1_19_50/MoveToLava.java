package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to move back into lava when on land. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveToLava(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Distance in blocks within the mob considers it has reached the goal. This is the {@code wiggle room} to stop the AI from bouncing back and forth trying to reach a specific spot */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* The number of blocks each tick that the mob will check within it's search range and height for a valid block to move to. A value of 0 will have the mob check every block within range in one tick */
    @JsonProperty("search_count") @Nullable Integer searchCount,
    /* Height in blocks the mob will look for lava to move towards. */
    @JsonProperty("search_height") @Nullable Integer searchHeight,
    /* The distance in blocks it will look for lava to move towards. */
    @JsonProperty("search_range") @Nullable Integer searchRange
) {
}
