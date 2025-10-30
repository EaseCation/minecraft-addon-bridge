package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Forces the entity to move {@code outside}, whatever that means. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveOutdoors(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* The radius away from the target block to count as reaching the goal. */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* The amount of times to try finding a random outdoors position before failing. */
    @JsonProperty("search_count") @Nullable Integer searchCount,
    /* The y range to search for an outdoors position for. */
    @JsonProperty("search_height") @Nullable Integer searchHeight,
    /* The x and z range to search for an outdoors position for. */
    @JsonProperty("search_range") @Nullable Integer searchRange,
    /* The cooldown time in seconds before the goal can be reused after pathfinding fails. */
    @JsonProperty("timeout_cooldown") @Nullable Double timeoutCooldown
) {
}
