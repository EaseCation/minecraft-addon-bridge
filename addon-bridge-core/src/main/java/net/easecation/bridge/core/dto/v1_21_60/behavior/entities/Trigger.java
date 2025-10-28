package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Trigger to fire. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Trigger {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Trigger_Variant0(
        String value
    ) implements Trigger {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Trigger_Variant2(
    ) implements Trigger {}
}
