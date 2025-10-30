package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BhgEffect(
    /* Effect name. */
    @JsonProperty("name") @Nullable String name,
    /* The duration of the effect. */
    @JsonProperty("duration") @Nullable Integer duration,
    /* The amplifier of the effect. */
    @JsonProperty("amplifier") @Nullable Integer amplifier
) {
}
