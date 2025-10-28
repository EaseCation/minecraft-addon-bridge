package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;

/* The minecraft molang definition that results in a boolean. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface MolangBoolean {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record MolangBoolean_Variant0(
        String value
    ) implements MolangBoolean {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record MolangBoolean_Variant1(
        Boolean value
    ) implements MolangBoolean {}
}
