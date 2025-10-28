package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows an entity to jump around a target. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record JumpAroundTarget(
    @JsonProperty("priority") @Nullable Priority priority,
    /* Enables collision checks when calculating the jump. Setting check_collision to true may affect performance and should be used with care. */
    @JsonProperty("check_collision") @Nullable Boolean checkCollision,
    /* Scaling temporarily applied to the entity's AABB bounds when jumping. A smaller bounding box reduces the risk of collisions during the jump. When check_collision is true it also increases the chance of being able to jump when close to obstacles. */
    @JsonProperty("entity_bounding_box_scale") @Nullable Object entityBoundingBoxScale,
    /* The jump angles in float degrees that are allowed when performing the jump. The order in which the angles are chosen is randomized. */
    @JsonProperty("jump_angles") @Nullable List<Double> jumpAngles,
    /* The time in seconds to spend in cooldown before this goal can be used again. */
    @JsonProperty("jump_cooldown_duration") @Nullable Double jumpCooldownDuration,
    /* The time in seconds to spend in cooldown after being hurt before this goal can be used again. */
    @JsonProperty("jump_cooldown_when_hurt_duration") @Nullable Double jumpCooldownWhenHurtDuration,
    /* The range deciding how close to and how far away from the target the landing position can be when jumping. */
    @JsonProperty("landing_distance_from_target") @Nullable VectorOf2Items landingDistanceFromTarget,
    /* This angle (in degrees) is used for controlling the spread when picking a landing position behind the target. A zero spread angle means the landing position will be straight behind the target with no variance. A 90 degree spread angle means the landing position can be up to 45 degrees to the left and to the right of the position straight behind the target's view direction. */
    @JsonProperty("landing_position_spread_degrees") @Nullable Integer landingPositionSpreadDegrees,
    /* If the entity was hurt within these last seconds, the jump<i>cooldown</i>when<i>hurt</i>duration will be used instead of jump<i>cooldown</i>duration. */
    @JsonProperty("last_hurt_duration") @Nullable Double lastHurtDuration,
    /* If the entity's line of sight towards its target is obstructed by an obstacle with a height below this number, the obstacle will be ignored, and the goal will try to find a valid landing position. */
    @JsonProperty("line_of_sight_obstruction_height_ignore") @Nullable Integer lineOfSightObstructionHeightIgnore,
    /* Maximum velocity a jump can be performed at. */
    @JsonProperty("max_jump_velocity") @Nullable Double maxJumpVelocity,
    /* The time in seconds to spend preparing for the jump. */
    @JsonProperty("prepare_jump_duration") @Nullable Double prepareJumpDuration,
    /* The number of blocks above the entity's head that has to be air for this goal to be usable. */
    @JsonProperty("required_vertical_space") @Nullable Integer requiredVerticalSpace,
    /* The number of blocks above and below from the jump target position that will be checked to find a surface to land on. */
    @JsonProperty("snap_to_surface_block_range") @Nullable Integer snapToSurfaceBlockRange,
    /* Target needs to be within this range for the jump to happen. */
    @JsonProperty("valid_distance_to_target") @Nullable VectorOf2Items validDistanceToTarget,
    /* Conditions that need to be met for the behavior to start. */
    @JsonProperty("filters") @Nullable Filters filters
) {
}
