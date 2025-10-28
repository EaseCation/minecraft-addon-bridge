package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;

/* The minecraft molang definition that results in a float. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface MolangNumber {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record MolangNumber_Variant0(
        String value
    ) implements MolangNumber {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record MolangNumber_Variant1(
        Double value
    ) implements MolangNumber {}
}
