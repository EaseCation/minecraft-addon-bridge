package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the entity to search within an area for farmland with air above it. If found, the entity will replace the air block by planting a seed item from its inventory on the farmland block. This goal requires "minecraft:inventory" and "minecraft:navigation" to execute. This goal will not execute if the entity does not have an item in its inventory. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HarvestFarmBlock(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* The maximum amount of time in seconds that the goal can take before searching for the first harvest block. The time is chosen between 0 and this number. */
    @JsonProperty("max_seconds_before_search") @Nullable Double maxSecondsBeforeSearch,
    /* The maximum amount of time in seconds that the goal can take before searching again, after failing to find a a harvest block already. The time is chosen between 0 and this number. */
    @JsonProperty("search_cooldown_max_seconds") @Nullable Double searchCooldownMaxSeconds,
    /* The number of randomly selected blocks each tick that the entity will check within its search range and height for a valid block to move to. A value of 0 will have the mob check every block within range in one tick. */
    @JsonProperty("search_count") @Nullable Integer searchCount,
    /* The height in blocks the entity will search within to find a valid target position. */
    @JsonProperty("search_height") @Nullable Integer searchHeight,
    /* The distance in blocks the entity will search within to find a valid target position. */
    @JsonProperty("search_range") @Nullable Integer searchRange,
    /* The amount of time in seconds that the goal will cooldown after a successful reap/sow, before it can start again. */
    @JsonProperty("seconds_until_new_task") @Nullable Double secondsUntilNewTask
) {
}
