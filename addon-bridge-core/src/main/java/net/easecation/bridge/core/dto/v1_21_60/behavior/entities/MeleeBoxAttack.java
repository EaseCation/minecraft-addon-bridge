package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Permits an entity to deal damage through a melee attack with reach calculations based on bounding boxes. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MeleeBoxAttack(
    @JsonProperty("priority") @Nullable Priority priority,
    @JsonProperty("speed_multiplier") @Nullable SpeedMultiplier speedMultiplier,
    /* Allows the entity to use this attack behavior, only once EVER. */
    @JsonProperty("attack_once") @Nullable Boolean attackOnce,
    /* Defines the entity types this entity will attack. */
    @JsonProperty("attack_types") @Nullable String attackTypes,
    /* If the entity is on fire, this allows the entity's target to catch on fire after being hi */
    @JsonProperty("can_spread_on_fire") @Nullable Boolean canSpreadOnFire,
    /* Cooldown time (in seconds) between attacks. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime,
    /* The attack reach of the entity will be a box with the size of the entity's bounds increased by this value in all horizontal directions. */
    @JsonProperty("horizontal_reach") @Nullable Double horizontalReach,
    /* Time (in seconds) to add to attack path recalculation when the target is beyond the "path<i>inner</i>boundary". */
    @JsonProperty("inner_boundary_time_increase") @Nullable Double innerBoundaryTimeIncrease,
    /* Unused. No effect on "minecraft:behavior.melee_attack". */
    @JsonProperty("max_dist") @Nullable Double maxDist,
    /* Maximum base time (in seconds) to recalculate new attack path to target (before increases applied). */
    @JsonProperty("max_path_time") @Nullable Double maxPathTime,
    /* Field of view (in degrees) when using the sensing component to detect an attack target. */
    @JsonProperty("melee_fov") @Nullable Double meleeFov,
    /* Minimum base time (in seconds) to recalculate new attack path to target (before increases applied). */
    @JsonProperty("min_path_time") @Nullable Double minPathTime,
    /* Defines the event to trigger when this entity successfully attacks. */
    @JsonProperty("on_attack") @Nullable Trigger onAttack,
    /* [UNDOCUMENTED] Defines the event to trigger when this entity successfully kills a target. */
    @JsonProperty("on_kill") @Nullable Trigger onKill,
    /* Time (in seconds) to add to attack path recalculation when the target is beyond the "path<i>outer</i>boundary". */
    @JsonProperty("outer_boundary_time_increase") @Nullable Double outerBoundaryTimeIncrease,
    /* Time (in seconds) to add to attack path recalculation when this entity cannot move along the current path. */
    @JsonProperty("path_fail_time_increase") @Nullable Double pathFailTimeIncrease,
    /* Distance at which to increase attack path recalculation by "inner<i>boundary</i>tick_increase". */
    @JsonProperty("path_inner_boundary") @Nullable Double pathInnerBoundary,
    /* Distance at which to increase attack path recalculation by "outer<i>boundary</i>tick_increase". */
    @JsonProperty("path_outer_boundary") @Nullable Double pathOuterBoundary,
    /* This entity will have a 1 in N chance to stop it's current attack, where N = "random<i>stop</i>interval". */
    @JsonProperty("random_stop_interval") @Nullable Integer randomStopInterval,
    /* Used with the base size of the entity to determine minimum target-distance before trying to deal attack damage. */
    @JsonProperty("reach_multiplier") @Nullable Double reachMultiplier,
    /* Toggles (on/off) the need to have a full path from the entity to the target when using this melee attack behavior. */
    @JsonProperty("require_complete_path") @Nullable Boolean requireCompletePath,
    /* Allows the actor to be set to persist upon targeting a player. */
    @JsonProperty("set_persistent") @Nullable Boolean setPersistent,
    /* Unused. No effect on "minecraft:behavior.melee_attack". */
    @JsonProperty("target_dist") @Nullable Double targetDist,
    /* Allows the entity to track the attack target, even if the entity has no sensing. */
    @JsonProperty("track_target") @Nullable Boolean trackTarget,
    /* Maximum rotation (in degrees), on the X-axis, this entity can rotate while trying to look at the target. */
    @JsonProperty("x_max_rotation") @Nullable Double xMaxRotation,
    /* Maximum rotation (in degrees), on the Y-axis, this entity can rotate its head while trying to look at the target. */
    @JsonProperty("y_max_head_rotation") @Nullable Double yMaxHeadRotation
) {
}
