package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;

/* The stacked by data component determines if the same item with different aux values can stack. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface StackedByData {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record StackedByData_Variant0(
        Boolean value
    ) implements StackedByData {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record StackedByData_Variant1(
        /* Also defines whether the item actors can merge while floating in the world. */
        @JsonProperty("value") Boolean value
    ) implements StackedByData {}
}
