package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;

/* If true the canopy uses a simple pattern. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SimplifyCanopy(
    /* If true the canopy uses a simple pattern. */
    Boolean value
) {
}
