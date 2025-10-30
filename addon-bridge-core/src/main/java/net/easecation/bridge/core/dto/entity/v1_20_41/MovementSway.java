package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This move control causes the mob to sway side to side giving the impression it is swimming. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MovementSway(
    /* The maximum number in degrees the mob can turn per tick. */
    @JsonProperty("max_turn") @Nullable Double maxTurn,
    /* Strength of the sway movement. */
    @JsonProperty("sway_amplitude") @Nullable Double swayAmplitude,
    /* Multiplier for the frequency of the sway movement. */
    @JsonProperty("sway_frequency") @Nullable Double swayFrequency
) {
}
