package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Movement speed multiplier of the mob when using this AI Goal. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpeedMultiplier(
    /* Movement speed multiplier of the mob when using this AI Goal. */
    Double value
) {
}
