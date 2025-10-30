package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to move into a random location within a village within the search range. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StrollTowardsVillage(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Time in seconds the mob has to wait before using the goal again. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime,
    /* Distance in blocks within the mob considers it has reached the goal. This is the {@code wiggle room} to stop the AI from bouncing back and forth trying to reach a specific spot */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* The distance in blocks to search for points inside villages. If <= 0, find the closest village regardless of distance. */
    @JsonProperty("search_range") @Nullable Integer searchRange,
    /* Movement speed multiplier of the mob when using this AI Goal. */
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* This is the chance that the mob will start this goal, from 0 to 1. */
    @JsonProperty("start_chance") @Nullable Double startChance
) {
}
