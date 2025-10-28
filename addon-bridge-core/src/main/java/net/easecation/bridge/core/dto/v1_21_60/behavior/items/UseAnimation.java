package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;

/* This component determines which animation plays when using the item. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface UseAnimation {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record UseAnimation_Variant0(
    ) implements UseAnimation {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record UseAnimation_Variant1(
        /* Which animation to play when using the item. */
        @JsonProperty("value") EeAnimation value
    ) implements UseAnimation {
    }
}
