package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;

/* The amount of light this block will emit in a range (0-15). Higher value means more light will be emitted. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LightEmission(
    /* The amount of light this block will emit in a range (0-15). Higher value means more light will be emitted. */
    Integer value
) {
}
