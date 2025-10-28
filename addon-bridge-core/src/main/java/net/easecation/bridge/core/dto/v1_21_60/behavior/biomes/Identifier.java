package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;

/* A minecraft identifier. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Identifier(
    /* A minecraft identifier. */
    String value
) {
}
