package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to move to attack a target. The goal ends if it has a horizontal collision or gets hit. Built to be used with flying mobs. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SwoopAttack(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Added to the base size of the entity, to determine the target's maximum allowable distance, when trying to deal attack damage. */
    @JsonProperty("damage_reach") @Nullable Double damageReach,
    /* Minimum and maximum cooldown time-range (in seconds) between each attempted swoop attack. */
    @JsonProperty("delay_range") @Nullable Range_a_B delayRange
) {
}
