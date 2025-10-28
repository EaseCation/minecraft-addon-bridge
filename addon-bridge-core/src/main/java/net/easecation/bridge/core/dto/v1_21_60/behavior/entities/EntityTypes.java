package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface EntityTypes {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntityTypes_Variant0(
    ) implements EntityTypes {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntityTypes_Variant1(
    ) implements EntityTypes {}
}
