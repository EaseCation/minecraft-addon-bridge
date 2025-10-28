package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;

/* The amount that light will be dampened when it passes through the block, in a range (0-15). Higher value means the light will be dampened more. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LightDampening(
    /* The amount that light will be dampened when it passes through the block, in a range (0-15). Higher value means the light will be dampened more. */
    Integer value
) {
}
