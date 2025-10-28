package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Distance in blocks at which the {@code spring} effect starts acting to keep this entity close to the entity that leashed it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SoftDistance(
    /* Distance in blocks at which the {@code spring} effect starts acting to keep this entity close to the entity that leashed it. */
    Double value
) {
}
