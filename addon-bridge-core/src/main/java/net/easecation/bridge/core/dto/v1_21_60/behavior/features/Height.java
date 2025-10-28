package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;

/* Number of layers for the canopy. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Height(
    /* Number of layers for the canopy. */
    Integer value
) {
}
