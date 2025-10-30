package net.easecation.bridge.core.dto.block.v1_19_0;

import com.fasterxml.jackson.annotation.*;

/* Property describing the breathability of the block, and whether it's treated as a solid block or a block of air. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Breathability(
    /* Property describing the breathability of the block, and whether it's treated as a solid block or a block of air. */
    String value
) {
}
