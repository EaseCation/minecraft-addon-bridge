package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;

/* The block thata forms the canopy of the tree. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LeafBlock(
    /* The block thata forms the canopy of the tree. */
    BlockReference value
) {
}
