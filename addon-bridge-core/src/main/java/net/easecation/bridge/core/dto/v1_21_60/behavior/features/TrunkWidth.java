package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;

/* The width of the tree trunk. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TrunkWidth(
    /* The width of the tree trunk. */
    Integer value
) {
}
