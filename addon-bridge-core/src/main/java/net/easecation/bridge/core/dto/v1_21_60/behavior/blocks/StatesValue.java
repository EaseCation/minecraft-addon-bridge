package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;

/* The key defines the name of a state, which must be properly namespaced. Each value is an array that contains all of the possible values of that state or an object defining a range of integers. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface StatesValue {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record StatesValue_Variant0(
    ) implements StatesValue {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record StatesValue_Variant1(
    ) implements StatesValue {
    }
}
