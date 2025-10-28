package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;

/* The max stack size component determines how many of the item can be stacked together. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface MaxStackSize {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record MaxStackSize_Variant0(
        Double value
    ) implements MaxStackSize {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record MaxStackSize_Variant1(
        /* How many of the item that can be stacked. */
        @JsonProperty("value") Double value
    ) implements MaxStackSize {}
}
