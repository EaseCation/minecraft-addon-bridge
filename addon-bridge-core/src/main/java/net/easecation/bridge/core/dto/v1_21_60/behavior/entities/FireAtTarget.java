package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows an entity to attack by firing a shot with a delay. Anchor and offset parameters of this component overrides the anchor and offset from projectile component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FireAtTarget(
    @JsonProperty("priority") @Nullable Priority priority,
    /* The cooldown time in seconds before this goal can be used again. */
    @JsonProperty("attack_cooldown") @Nullable Double attackCooldown,
    /* Target needs to be within this range for the attack to happen. */
    @JsonProperty("attack_range") @Nullable VectorOf3Items attackRange,
    /* Entity anchor for the projectile spawn location. */
    @JsonProperty("owner_anchor") @Nullable Integer ownerAnchor,
    /* Offset vector from the owner_anchor. */
    @JsonProperty("owner_offset") @Nullable VectorOf3Items ownerOffset,
    /* Entity anchor for projectile target. */
    @JsonProperty("target_anchor") @Nullable Integer targetAnchor,
    /* Offset vector from the target_anchor. */
    @JsonProperty("target_offset") @Nullable VectorOf3Items targetOffset,
    /* Time in seconds between firing the projectile and ending the goal. */
    @JsonProperty("post_shoot_delay") @Nullable Double postShootDelay,
    /* Time in seconds before firing the projectile. */
    @JsonProperty("pre_shoot_delay") @Nullable Double preShootDelay,
    /* Actor definition to use as projectile for the ranged attack. The actor must be a projectile. */
    @JsonProperty("projectile_def") @Nullable String projectileDef,
    /* Field of view (in degrees) when using sensing to detect a target for attack. */
    @JsonProperty("ranged_fov") @Nullable Double rangedFov,
    /* Maximum head rotation (in degrees), on the X-axis, that this entity can apply while trying to look at the target. */
    @JsonProperty("max_head_rotation_x") @Nullable Double maxHeadRotationX,
    /* Maximum head rotation (in degrees), on the Y-axis, that this entity can apply while trying to look at the target. */
    @JsonProperty("max_head_rotation_y") @Nullable Double maxHeadRotationY,
    /* Conditions that need to be met for the behavior to start. */
    @JsonProperty("filters") @Nullable Filters filters
) {
}
