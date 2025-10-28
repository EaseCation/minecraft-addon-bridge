package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;

/* The liquid clipped component determines whether the item interacts with liquid blocks on use. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface LiquidClipped {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record LiquidClipped_Variant0(
        Boolean value
    ) implements LiquidClipped {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record LiquidClipped_Variant1(
        /* Whether the item interacts with liquid blocks on use. */
        @JsonProperty("value") Boolean value
    ) implements LiquidClipped {}
}
