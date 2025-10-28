package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BiColor {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BiColor_Variant0(
        String value
    ) implements BiColor {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BiColor_Variant1(
    ) implements BiColor {}
}
