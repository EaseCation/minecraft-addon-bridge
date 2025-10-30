package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows an entity to move around a target. If the entity is too close (i.e. closer than destination range min and height difference limit) it will try to move away from its target. If the entity is too far away from its target it will try to move closer to a random position within the destination range. A randomized amount of those positions will be behind the target, and the spread can be tweaked with 'destination<i>pos</i>search<i>spread</i>degrees'. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveAroundTarget(
    @JsonProperty("priority") @Nullable Integer priority,
    /* This angle (in degrees) is used for controlling the spread when picking a destination position behind the target. A zero spread angle means the destination position will be straight behind the target with no variance. A 90 degree spread angle means the destination position can be up to 45 degrees to the left and to the right of the position straight behind the target's view direction. */
    @JsonProperty("destination_pos_search_spread_degrees") @Nullable Double destinationPosSearchSpreadDegrees,
    /* The range of distances from the target entity within which the goal should look for a position to move the owner entity to. */
    @JsonProperty("destination_position_range") @Nullable VectorOf2Items destinationPositionRange,
    /* Distance in height (in blocks) between the owner entity and the target has to be less than this value when owner checks if it is too close and should move away from the target. This value needs to be bigger than zero for the move away logic to trigger. */
    @JsonProperty("height_difference_limit") @Nullable Double heightDifferenceLimit,
    /* Horizontal search distance (in blocks) when searching for a position to move away from target. */
    @JsonProperty("horizontal_search_distance") @Nullable Integer horizontalSearchDistance,
    /* The speed with which the entity should move to its target position. */
    @JsonProperty("movement_speed") @Nullable Double movementSpeed,
    /* Number of ticks needed to complete a stay at the block. */
    @JsonProperty("vertical_search_distance") @Nullable Integer verticalSearchDistance,
    /* Conditions that need to be met for the behavior to start. */
    @JsonProperty("filters") @Nullable Filters filters
) {
}
