package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Adds a cooldown to a mob. The intention of this cooldown is to be used to prevent the mob from attempting to aquire new attack targets. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AttackCooldown(
    /* Event to be run when the cooldown is complete. */
    @JsonProperty("attack_cooldown_complete_event") @Nullable Trigger attackCooldownCompleteEvent,
    /* Amount of time in seconds for the cooldown. Can be specified as a number or a pair of numbers (Minimum and max). */
    @JsonProperty("attack_cooldown_time") @Nullable Object attackCooldownTime
) {
}
