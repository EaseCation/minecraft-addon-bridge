package net.easecation.bridge.core.dto.item.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines both the durability threshold, and the effects emitted when that threshold is met. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DurabilitySensor(
    /* The effects are emitted when the item durability value is less than or equal to this value. */
    @JsonProperty("durability") @Nullable Integer durability,
    /* Particle effect to emit when the threshold is met. */
    @JsonProperty("particle_type") @Nullable String particleType,
    /* Sound effect to emit when the threshold is met. */
    @JsonProperty("sound_event") @Nullable String soundEvent
) {
}
