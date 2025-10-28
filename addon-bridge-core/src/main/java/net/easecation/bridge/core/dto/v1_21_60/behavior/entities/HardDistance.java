package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Distance in blocks at which the leash stiffens, restricting movement. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HardDistance(
    /* Distance in blocks at which the leash stiffens, restricting movement. */
    Double value
) {
}
