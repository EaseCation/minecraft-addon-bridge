package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;

/* A minecraft block identifier. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BlockIdentifier(
    /* A minecraft block identifier. */
    String value
) {
}
