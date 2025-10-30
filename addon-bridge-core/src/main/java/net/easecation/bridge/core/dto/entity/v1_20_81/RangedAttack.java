package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to use ranged attacks like shooting arrows. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RangedAttack(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Alternative to "attack<i>interval</i>min" & "attack<i>interval</i>max". Consistent reload-time (in seconds), when not using a charged shot. Does not scale with target-distance. */
    @JsonProperty("attack_interval") @Nullable Double attackInterval,
    /* Maximum bound for reload-time range (in seconds), when not using a charged shot. Reload-time range scales with target-distance. */
    @JsonProperty("attack_interval_max") @Nullable Double attackIntervalMax,
    /* Minimum bound for reload-time range (in seconds), when not using a charged shot. Reload-time range scales with target-distance. */
    @JsonProperty("attack_interval_min") @Nullable Double attackIntervalMin,
    /* Minimum distance to target before this entity will attempt to shoot. */
    @JsonProperty("attack_radius") @Nullable Double attackRadius,
    /* Minimum distance the target can be for this mob to fire. If the target is closer, this mob will move first before firing */
    @JsonProperty("attack_radius_min") @Nullable Double attackRadiusMin,
    /* Time (in seconds) between each individual shot when firing a burst of shots from a charged up attack. */
    @JsonProperty("burst_interval") @Nullable Double burstInterval,
    /* Number of shots fired every time the attacking entity uses a charged up attack. */
    @JsonProperty("burst_shots") @Nullable Integer burstShots,
    /* Time (in seconds, then add "charge<i>shoot</i>trigger"), before a charged up attack is done charging. Charge-time decays while target is not in sight. */
    @JsonProperty("charge_charged_trigger") @Nullable Double chargeChargedTrigger,
    /* Amount of time (in seconds, then doubled) a charged shot must be charging before reloading burst shots. Charge-time decays while target is not in sight. */
    @JsonProperty("charge_shoot_trigger") @Nullable Double chargeShootTrigger,
    /* Field of view (in degrees) when using sensing to detect a target for attack. */
    @JsonProperty("ranged_fov") @Nullable Double rangedFov,
    /* Allows the actor to be set to persist upon targeting a player. */
    @JsonProperty("set_persistent") @Nullable Boolean setPersistent,
    /* If a swing animation (using variable.attack_time) exists, this causes the actor to swing their arm(s) upon firing the ranged attack. */
    @JsonProperty("swing") @Nullable Boolean swing,
    /* Minimum amount of time (in seconds) the attacking entity needs to see the target before moving toward it. */
    @JsonProperty("target_in_sight_time") @Nullable Double targetInSightTime,
    /* Maximum rotation (in degrees), on the X-axis, this entity can rotate while trying to look at the target. */
    @JsonProperty("x_max_rotation") @Nullable Double xMaxRotation,
    /* Maximum rotation (in degrees), on the Y-axis, this entity can rotate its head while trying to look at the target. */
    @JsonProperty("y_max_head_rotation") @Nullable Double yMaxHeadRotation
) {
}
