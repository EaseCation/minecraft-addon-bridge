package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines an entity's melee attack and any additional effects on it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Attack(
    /* Range of the random amount of damage the melee attack deals. A negative value can heal the entity instead of hurting it. */
    @JsonProperty("damage") Range_a_B_ damage,
    /* Identifier of the status ailment to apply to an entity attacked by this entity's melee attack. */
    @JsonProperty("effect_name") @Nullable Effect effectName,
    /* Duration in seconds of the status ailment applied to the damaged entity. */
    @JsonProperty("effect_duration") @Nullable Object effectDuration
) {
}
