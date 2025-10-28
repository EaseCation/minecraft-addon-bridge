package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;

/* The radius of the canopy. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Radius(
    /* The radius of the canopy. */
    Integer value
) {
}
