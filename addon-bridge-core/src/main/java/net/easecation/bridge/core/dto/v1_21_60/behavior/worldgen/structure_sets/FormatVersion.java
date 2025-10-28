package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.structure_sets;

import com.fasterxml.jackson.annotation.*;

/* A version that tells minecraft what type of data format can be expected when reading this file. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FormatVersion(
    /* A version that tells minecraft what type of data format can be expected when reading this file. */
    String value
) {
}
