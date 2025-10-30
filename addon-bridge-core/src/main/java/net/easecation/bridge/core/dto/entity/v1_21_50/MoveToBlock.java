package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows mob to move towards a block. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveToBlock(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Distance in blocks within the mob considers it has reached the goal. This is the "wiggle room" to stop the AI from bouncing back and forth trying to reach a specific spot */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* Event to run on completing a stay of stay_duration at the block. */
    @JsonProperty("on_stay_completed") @Nullable Trigger onStayCompleted,
    /* Event to run on block reached. */
    @JsonProperty("on_reach") @Nullable Trigger onReach,
    /* Chance to start the behavior (applied after each random tick_interval). */
    @JsonProperty("start_chance") @Nullable Double startChance,
    /* The distance in blocks that the mob will look for the block. */
    @JsonProperty("search_range") @Nullable Integer searchRange,
    /* The height in blocks that the mob will look for the block. */
    @JsonProperty("search_height") @Nullable Integer searchHeight,
    /* Number of ticks needed to complete a stay at the block. */
    @JsonProperty("stay_duration") @Nullable Double stayDuration,
    /* Kind of block to find fitting the specification. Valid values are "random" and "nearest". */
    @JsonProperty("target_selection_method") @Nullable String targetSelectionMethod,
    /* Offset to add to the selected target position. */
    @JsonProperty("target_offset") @Nullable List<Double> targetOffset,
    /* Block types to move to. */
    @JsonProperty("target_blocks") @Nullable List<EntityBb> targetBlocks,
    /* Filters to apply on the target blocks. Target blocks are only valid if the filters are true. */
    @JsonProperty("target_block_filters") @Nullable Filters targetBlockFilters,
    /* Average interval in ticks to try to run this behavior. */
    @JsonProperty("tick_interval") @Nullable Integer tickInterval
) {
}
