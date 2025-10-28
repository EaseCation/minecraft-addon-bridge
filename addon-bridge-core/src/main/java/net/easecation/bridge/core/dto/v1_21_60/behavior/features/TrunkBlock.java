package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;

/* The block that forms the tree trunk. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TrunkBlock(
    /* The block that forms the tree trunk. */
    BlockReference value
) {
}
