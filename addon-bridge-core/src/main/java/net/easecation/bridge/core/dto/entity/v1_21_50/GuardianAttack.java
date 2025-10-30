package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this entity to use a laser beam attack. Can only be used by Guardians and Elder Guardians. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GuardianAttack(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Amount of additional damage dealt from an elder guardian's magic attack. */
    @JsonProperty("elder_extra_magic_damage") @Nullable Integer elderExtraMagicDamage,
    /* In hard difficulty, amount of additional damage dealt from a guardian's magic attack. */
    @JsonProperty("hard_mode_extra_magic_damage") @Nullable Integer hardModeExtraMagicDamage,
    /* Amount of damage dealt from a guardian's magic attack. Magic attack damage is added to the guardian's base attack damage. */
    @JsonProperty("magic_damage") @Nullable Integer magicDamage,
    /* Guardian attack behavior stops if the target is closer than this distance (doesn't apply to elders). */
    @JsonProperty("min_distance") @Nullable Double minDistance,
    /* Time (in seconds) to wait after starting an attack before playing the guardian attack sound. */
    @JsonProperty("sound_delay_time") @Nullable Double soundDelayTime,
    /* Maximum rotation (in degrees), on the X-axis, this entity can rotate while trying to look at the target. */
    @JsonProperty("x_max_rotation") @Nullable Double xMaxRotation,
    /* Maximum rotation (in degrees), on the Y-axis, this entity can rotate its head while trying to look at the target. */
    @JsonProperty("y_max_head_rotation") @Nullable Double yMaxHeadRotation
) {
}
