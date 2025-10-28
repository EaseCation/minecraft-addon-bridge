package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BbRangeorint {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BbRangeorint_Variant0(
        Integer value
    ) implements BbRangeorint {}
}
