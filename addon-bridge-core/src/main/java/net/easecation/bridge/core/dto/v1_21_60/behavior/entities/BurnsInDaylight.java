package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Specifies if/how a mob burns in daylight. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BurnsInDaylight {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BurnsInDaylight_Variant0(
        Boolean value
    ) implements BurnsInDaylight {}
}
