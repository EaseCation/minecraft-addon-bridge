package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Dcb {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Dcb_Variant0(
    ) implements Dcb {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Dcb_Value(
        Double value
    ) implements Dcb {}
}
