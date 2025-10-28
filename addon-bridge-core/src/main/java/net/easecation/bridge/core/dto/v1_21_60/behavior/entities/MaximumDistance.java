package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Distance in blocks at which the leash breaks. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MaximumDistance(
    /* Distance in blocks at which the leash breaks. */
    Double value
) {
}
