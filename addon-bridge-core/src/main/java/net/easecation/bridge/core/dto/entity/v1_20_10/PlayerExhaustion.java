package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the player's need for food. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerExhaustion(
    /* The maximum player saturation value. */
    @JsonProperty("value") @Nullable Double value,
    /* The maximum player saturation value. */
    @JsonProperty("max") @Nullable Double max
) {
}
