package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the entity to move toward a target, and drop an item near the target. This goal requires a "minecraft:navigation" to execute. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DropItemFor(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* The list of conditions another entity must meet to be a valid target to drop an item for. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes,
    /* Total time that the goal is on cooldown before it can be used again. */
    @JsonProperty("cooldown") @Nullable Double cooldown,
    /* The percent chance the entity will drop an item when using this goal. */
    @JsonProperty("drop_item_chance") @Nullable Double dropItemChance,
    /* Distance in blocks within the entity considers it has reached it's target position. */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* The loot table that contains the possible loot the entity can drop with this goal. */
    @JsonProperty("loot_table") @Nullable String lootTable,
    /* The maximum height the entities head will look at when dropping the item. The entity will always be looking at its target. */
    @JsonProperty("max_head_look_at_height") @Nullable Double maxHeadLookAtHeight,
    /* If the target position is farther away than this distance on any tick, the entity will teleport to the target position. */
    @JsonProperty("minimum_teleport_distance") @Nullable Double minimumTeleportDistance,
    /* The preferred distance the entity tries to be from the target it is dropping an item for. */
    @JsonProperty("offering_distance") @Nullable Double offeringDistance,
    /* The event to trigger when the entity attempts to drop an item. */
    @JsonProperty("on_drop_attempt") @Nullable Trigger onDropAttempt,
    /* The number of blocks each tick that the entity will check within its search range and height for a valid block to move to. A value of 0 will have the mob check every block within range in one tick. */
    @JsonProperty("search_count") @Nullable Integer searchCount,
    /* The Height in blocks the entity will search within to find a valid target position. */
    @JsonProperty("search_height") @Nullable Integer searchHeight,
    /* The distance in blocks the entity will search within to find a valid target position. */
    @JsonProperty("search_range") @Nullable Integer searchRange,
    /* The numbers of seconds that will pass before the dropped entity can be picked up from the ground. */
    @JsonProperty("seconds_before_pickup") @Nullable Double secondsBeforePickup,
    /* The range in blocks within which the entity searches to find a target to drop an item for. */
    @JsonProperty("target_range") @Nullable VectorOf3Items targetRange,
    /* When the entity teleports, offset the teleport position by this many blocks in the X, Y, and Z coordinate. */
    @JsonProperty("teleport_offset") @Nullable VectorOf3Items teleportOffset,
    /* The valid times of day that this goal can be used. For reference: noon is 0.0, sunset is 0.25, midnight is 0.5, and sunrise is 0.75, and back to noon for 1.0. */
    @JsonProperty("time_of_day_range") @Nullable Range_a_B timeOfDayRange
) {
}
