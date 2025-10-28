package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to search within an area for a growable crop block. If found, the mob will use any available fertilizer in their inventory on the crop. This goal will not execute if the mob does not have a fertilizer item in its inventory. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FertilizeFarmBlock(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Distance in blocks within the mob considers it has reached it's target position. */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* The maximum number of times the mob will use fertilzer on the target block. */
    @JsonProperty("max_fertilizer_usage") @Nullable Integer maxFertilizerUsage,
    /* The maximum amount of time in seconds that the goal can take before searching again. The time is chosen between 0 and this number. */
    @JsonProperty("search_cooldown_max_seconds") @Nullable Double searchCooldownMaxSeconds,
    /* The number of randomly selected blocks each tick that the mob will check within its search range and height for a valid block to move to. A value of 0 will have the mob check every block within range in one tick. */
    @JsonProperty("search_count") @Nullable Integer searchCount,
    /* The Height in blocks the mob will search within to find a valid target position. */
    @JsonProperty("search_height") @Nullable Integer searchHeight,
    /* The distance in blocks the mob will search within to find a valid target position. */
    @JsonProperty("search_range") @Nullable Integer searchRange
) {
}
