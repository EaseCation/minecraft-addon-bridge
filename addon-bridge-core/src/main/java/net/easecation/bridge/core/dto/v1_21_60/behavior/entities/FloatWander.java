package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to float around like the Ghast. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FloatWander(
    @JsonProperty("priority") @Nullable Priority priority,
    /* If true, the mob will have an additional buffer zone around it to avoid collisions with blocks when picking a position to wander to. */
    @JsonProperty("additional_collision_buffer") @Nullable Boolean additionalCollisionBuffer,
    /* If true allows the mob to navigate through liquids on its way to the target position. */
    @JsonProperty("allow_navigating_through_liquids") @Nullable Boolean allowNavigatingThroughLiquids,
    /* Distance in blocks on ground that the mob will look for a new spot to move to. Must be at least 1 */
    @JsonProperty("xz_dist") @Nullable Integer xzDist,
    /* Distance in blocks that the mob will look up or down for a new spot to move to. Must be at least 1 */
    @JsonProperty("y_dist") @Nullable Integer yDist,
    /* Height in blocks to add to the selected target position. */
    @JsonProperty("y_offset") @Nullable Double yOffset,
    /* If true, the point has to be reachable to be a valid target. */
    @JsonProperty("must_reach") @Nullable Boolean mustReach,
    /* If true, the MoveControl flag will be added to the behavior which means that it can no longer be active at the same time as other behaviors with MoveControl. */
    @JsonProperty("float_wander_has_move_control") @Nullable Boolean floatWanderHasMoveControl,
    /* If true, will prioritize finding random positions in the vicinity of surfaces, i.e. blocks that are not Air or Liquid. */
    @JsonProperty("navigate_around_surface") @Nullable Boolean navigateAroundSurface,
    /* If true, the mob will randomly pick a new point while moving to the previously selected one. */
    @JsonProperty("random_reselect") @Nullable Boolean randomReselect,
    /* The horizontal distance in blocks that the goal will check for a surface from a candidate position. Only valid when {@code navigate<i>around</i>surface} is true. */
    @JsonProperty("surface_xz_dist") @Nullable Integer surfaceXzDist,
    /* The vertical distance in blocks that the goal will check for a surface from a candidate position. Only valid when {@code navigate<i>around</i>surface} is true. */
    @JsonProperty("surface_y_dist") @Nullable Integer surfaceYDist,
    /* If true, the mob will respect home position restrictions when choosing new target positions. If false, it will choose target position without considering home restrictions. */
    @JsonProperty("use_home_position_restriction") @Nullable Boolean useHomePositionRestriction,
    /* Range of time in seconds the mob will float around before landing and choosing to do something else. */
    @JsonProperty("float_duration") @Nullable Range_a_B_ floatDuration
) {
}
