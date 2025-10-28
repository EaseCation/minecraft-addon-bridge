package net.easecation.bridge.core.dto.v1_21_60.behavior.lighting;

import com.fasterxml.jackson.annotation.*;

/* The color of the light emitted by the block, in RGB format or hex format. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface LightingColor {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record LightingColor_Variant0(
        String value
    ) implements LightingColor {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record LightingColor_Variant1(
    ) implements LightingColor {}
}
