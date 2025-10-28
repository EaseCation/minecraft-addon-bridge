package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Dcc {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Dcc_Variant0(
    ) implements Dcc {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Dcc_Value(
        Integer value
    ) implements Dcc {}
}
