package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Plays the provided sounds and activates the {@code SONIC BOOM} actor flag during the specified duration */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SonicBoom(
    @JsonProperty("priority") @Nullable Priority priority,
    @JsonProperty("speed_multiplier") @Nullable SpeedMultiplier speedMultiplier,
    /* Cooldown in seconds required after using this attack until the entity can use sonic boom again. */
    @JsonProperty("attack_cooldown") @Nullable Double attackCooldown,
    /* Attack damage of the sonic boom. */
    @JsonProperty("attack_damage") @Nullable Double attackDamage,
    /* Horizontal range (in blocks) at which the sonic boom can damage the target. */
    @JsonProperty("attack_range_horizontal") @Nullable Double attackRangeHorizontal,
    /* Vertical range (in blocks) at which the sonic boom can damage the target. */
    @JsonProperty("attack_range_vertical") @Nullable Double attackRangeVertical,
    /* Sound event for the attack. */
    @JsonProperty("attack_sound") @Nullable SoundEvent attackSound,
    /* Sound event for the charge up. */
    @JsonProperty("charge_sound") @Nullable SoundEvent chargeSound,
    /* Goal duration in seconds. */
    @JsonProperty("duration") @Nullable Double duration,
    /* Duration in seconds until the attack sound is played. */
    @JsonProperty("duration_until_attack_sound") @Nullable Double durationUntilAttackSound,
    /* Height cap of the attack knockback's vertical delta. */
    @JsonProperty("knockback_height_cap") @Nullable Double knockbackHeightCap,
    /* Horizontal strength of the attack's knockback applied to the attack target. */
    @JsonProperty("knockback_horizontal_strength") @Nullable Double knockbackHorizontalStrength,
    /* Vertical strength of the attack's knockback applied to the attack target. */
    @JsonProperty("knockback_vertical_strength") @Nullable Double knockbackVerticalStrength
) {
}
