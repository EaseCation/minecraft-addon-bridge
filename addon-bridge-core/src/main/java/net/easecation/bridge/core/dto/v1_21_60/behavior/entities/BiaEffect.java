package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BiaEffect(
    /* Effect name. */
    @JsonProperty("name") @Nullable Effect name,
    /* The duration of the effect. */
    @JsonProperty("duration") @Nullable Object duration,
    /* The amplifier of the effect. */
    @JsonProperty("amplifier") @Nullable Integer amplifier
) {
}
