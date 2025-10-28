package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to pick up items on the ground. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PickupItems(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* If true, the mob can pickup any item. */
    @JsonProperty("can_pickup_any_item") @Nullable Boolean canPickupAnyItem,
    /* If true, the mob can pickup items to its hand or armor slots. */
    @JsonProperty("can_pickup_to_hand_or_equipment") @Nullable Boolean canPickupToHandOrEquipment,
    /* Amount of time an offended entity needs before being willing to pick up items. */
    @JsonProperty("cooldown_after_being_attacked") @Nullable Double cooldownAfterBeingAttacked,
    /* List of items this mob will not pick up. */
    @JsonProperty("excluded_items") @Nullable List<EntitiesBb> excludedItems,
    /* Distance in blocks within the mob considers it has reached the goal. This is the {@code wiggle room} to stop the AI from bouncing back and forth trying to reach a specific spot. */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* Maximum distance this mob will look for items to pick up. */
    @JsonProperty("max_dist") @Nullable Double maxDist,
    /* Height in blocks the mob will look for items to pick up. */
    @JsonProperty("search_height") @Nullable Double searchHeight,
    /* If true, depending on the difficulty, there is a random chance that the mob may not be able to pickup items. */
    @JsonProperty("pickup_based_on_chance") @Nullable Boolean pickupBasedOnChance,
    /* If true, the mob will pickup the same item as the item in its hand. */
    @JsonProperty("pickup_same_items_as_in_hand") @Nullable Boolean pickupSameItemsAsInHand,
    /* If true, this mob will chase after the target as long as it's a valid target. */
    @JsonProperty("track_target") @Nullable Boolean trackTarget
) {
}
