package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the player's need for food. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerSaturation(
    /* The initial value of player saturation. */
    @JsonProperty("value") @Nullable Integer value,
    /* The maximum player saturation value. */
    @JsonProperty("max") @Nullable Integer max
) {
}
