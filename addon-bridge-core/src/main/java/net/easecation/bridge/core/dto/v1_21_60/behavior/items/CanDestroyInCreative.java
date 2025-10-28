package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;

/* The can destroy in creative component determines if the item will break blocks in creative when swinging. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface CanDestroyInCreative {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record CanDestroyInCreative_Variant0(
        Boolean value
    ) implements CanDestroyInCreative {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record CanDestroyInCreative_Variant1(
        /* Whether the item can destroy blocks while in creative */
        @JsonProperty("value") Boolean value
    ) implements CanDestroyInCreative {}
}
