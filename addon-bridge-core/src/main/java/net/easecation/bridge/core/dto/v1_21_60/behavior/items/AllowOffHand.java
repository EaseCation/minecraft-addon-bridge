package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;

/* The allow off hand component determines whether the item can be placed in the off hand slot of the inventory. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface AllowOffHand {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record AllowOffHand_Variant0(
        Boolean value
    ) implements AllowOffHand {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record AllowOffHand_Variant1(
        /* Whether the item can be placed in the off hand slot */
        @JsonProperty("value") Boolean value
    ) implements AllowOffHand {}
}
