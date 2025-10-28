package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to move into a random location within a village. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveToVillage(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Time in seconds the mob has to wait before using the goal again. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime,
    /* Distance in blocks within the mob considers it has reached the goal. This is the {@code wiggle room} to stop the AI from bouncing back and forth trying to reach a specific spot */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* The distance in blocks to search for villages. If <= 0, find the closest village regardless of distance. */
    @JsonProperty("search_range") @Nullable Integer searchRange
) {
}
