package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Range {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Range_Variant0(
        Integer value
    ) implements Range {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Range_Variant1(
        /* Lower bound of the vaues. */
        @JsonProperty("range_min") @Nullable Integer rangeMin,
        /* Upper bound of the vaues. */
        @JsonProperty("range_max") @Nullable Integer rangeMax
    ) implements Range {}
}
