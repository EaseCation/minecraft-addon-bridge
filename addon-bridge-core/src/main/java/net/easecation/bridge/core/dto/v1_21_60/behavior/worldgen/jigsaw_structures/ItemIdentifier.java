package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;

/* A minecraft item identifier. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemIdentifier(
    /* A minecraft item identifier. */
    String value
) {
}
