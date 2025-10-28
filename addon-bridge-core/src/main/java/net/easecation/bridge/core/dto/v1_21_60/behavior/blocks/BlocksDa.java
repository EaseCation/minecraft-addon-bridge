package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BlocksDa {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BlocksDa_Variant0(
    ) implements BlocksDa {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BlocksDa_Variant1(
    ) implements BlocksDa {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BlocksDa_Variant2(
    ) implements BlocksDa {}
}
