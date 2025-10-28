package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;

/* This component determines if an item is rendered like a tool while in hand. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface HandEquipped {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record HandEquipped_Variant0(
        Boolean value
    ) implements HandEquipped {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record HandEquipped_Variant1(
        /* If the item is rendered like a tool while in hand. */
        @JsonProperty("value") Boolean value
    ) implements HandEquipped {}
}
