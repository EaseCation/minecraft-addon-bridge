package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* A minecraft entity identifier. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EntityIdentifier(
    /* A minecraft entity identifier. */
    String value
) {
}
