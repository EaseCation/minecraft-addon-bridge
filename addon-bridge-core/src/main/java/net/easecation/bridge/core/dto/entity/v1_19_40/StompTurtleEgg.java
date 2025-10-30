package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this mob to stomp turtle eggs. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StompTurtleEgg(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Distance in blocks within the mob considers it has reached the goal. This is the {@code wiggle room} to stop the AI from bouncing back and forth trying to reach a specific spot */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* A random value to determine when to randomly move somewhere. This has a 1/interval chance to choose this goal */
    @JsonProperty("interval") @Nullable Integer interval,
    /* The number of blocks each tick that the mob will check within it's search range and height for a valid block to move to. A value of 0 will have the mob check every block within range in one tick */
    @JsonProperty("search_count") @Nullable Integer searchCount,
    /* Height in blocks the mob will look for turtle eggs to move towards. */
    @JsonProperty("search_height") @Nullable Integer searchHeight,
    /* The distance in blocks it will look for turtle eggs to move towards. */
    @JsonProperty("search_range") @Nullable Integer searchRange
) {
}
