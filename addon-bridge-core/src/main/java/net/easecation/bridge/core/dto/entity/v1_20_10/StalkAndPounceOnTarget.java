package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows an entity to stalk a specific target. Once within range of the target, the entity will then leap at the target and deal damage based upon its attack attribute. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StalkAndPounceOnTarget(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The amount of time the mob will be interested before pouncing. This happens when the mob is within range of pouncing */
    @JsonProperty("interest_time") @Nullable Double interestTime,
    /* The distance in blocks the mob jumps in the direction of its target. */
    @JsonProperty("leap_distance") @Nullable Double leapDistance,
    /* The height in blocks the mob jumps when leaping at its target. */
    @JsonProperty("leap_height") @Nullable Double leapHeight,
    /* The maximum distance away a target can be before the mob gives up on stalking. */
    @JsonProperty("max_stalk_dist") @Nullable Double maxStalkDist,
    /* The maximum distance away from the target in blocks to begin pouncing at the target. */
    @JsonProperty("pounce_max_dist") @Nullable Double pounceMaxDist,
    /* Allows the actor to be set to persist upon targeting a player. */
    @JsonProperty("set_persistent") @Nullable Boolean setPersistent,
    /* The movement speed in which you stalk your target. */
    @JsonProperty("stalk_speed") @Nullable Double stalkSpeed,
    /* The Maximum distance away from the target when landing from the pounce that will still result in damaging the target. */
    @JsonProperty("strike_dist") @Nullable Double strikeDist,
    /* The amount of time the mob will be stuck if they fail and land on a block they can be stuck on. */
    @JsonProperty("stuck_time") @Nullable Double stuckTime,
    /* The distance in blocks the mob jumps in the direction of their target. */
    @JsonProperty("leap_dist") @Nullable Double leapDist,
    /* Filters to apply on the block the mob lands on to determine if it is valid for getting stuck. */
    @JsonProperty("stuck_blocks") @Nullable Filters stuckBlocks
) {
}
