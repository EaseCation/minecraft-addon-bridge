package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows this entity to locate a random target block that it can path find to. Once found, the entity will move towards it and dig up an item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomLookAround(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Goal cooldown range in seconds. */
    @JsonProperty("cooldown_range") @Nullable Object cooldownRange,
    /* Digging duration in seconds. */
    @JsonProperty("digging_duration_range") @Nullable VectorOf2Items diggingDurationRange,
    /* Amount of retries to find a valid target position within search range. */
    @JsonProperty("find_valid_position_retries") @Nullable Double findValidPositionRetries,
    /* Distance in blocks within the entity to considers it has reached it's target position. */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* File path relative to the resource pack root for items to spawn list (loot table format). */
    @JsonProperty("item_table") @Nullable String itemTable,
    /* Event to run when the goal ends searching has begins digging. */
    @JsonProperty("on_digging_start") @Nullable Trigger onDiggingStart,
    /* Event to run when the goal failed while in digging state. */
    @JsonProperty("on_fail_during_digging") @Nullable Trigger onFailDuringDigging,
    /* Event to run when the goal failed while in searching state. */
    @JsonProperty("on_fail_during_searching") @Nullable Trigger onFailDuringSearching,
    /* Event to run when the goal find a item. */
    @JsonProperty("on_item_found") @Nullable Trigger onItemFound,
    /* Event to run when the goal starts searching. */
    @JsonProperty("on_searching_start") @Nullable Trigger onSearchingStart,
    /* Event to run when searching and digging has ended. */
    @JsonProperty("on_success") @Nullable Trigger onSuccess,
    /* Width and length of the volume around the entity used to find a valid target position */
    @JsonProperty("search_range_xz") @Nullable Double searchRangeXz,
    /* Height of the volume around the entity used to find a valid target position */
    @JsonProperty("search_range_y") @Nullable Double searchRangeY,
    /* Digging duration before spawning item in seconds. */
    @JsonProperty("spawn_item_after_seconds") @Nullable Double spawnItemAfterSeconds,
    /* Distance to offset the item's spawn location in the direction the mob is facing. */
    @JsonProperty("spawn_item_pos_offset") @Nullable Double spawnItemPosOffset,
    /* List of target block types the goal will look to dig on. Overrides the default list. */
    @JsonProperty("target_blocks") @Nullable List<BlockReference> targetBlocks,
    /* Dig target position offset from the feet position of the mob in their facing direction. */
    @JsonProperty("target_dig_position_offset") @Nullable Double targetDigPositionOffset
) {
}
