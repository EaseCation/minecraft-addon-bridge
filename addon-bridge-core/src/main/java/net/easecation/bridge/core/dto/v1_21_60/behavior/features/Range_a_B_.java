package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A described range. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Range_a_B_ {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Range_a_B__Variant0(
        Double value
    ) implements Range_a_B_ {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Range_a_B__Variant1(
    ) implements Range_a_B_ {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Range_a_B__Variant2(
        /* The minimum value of the range. */
        @JsonProperty("range_min") @Nullable Double rangeMin,
        /* The maximum value of the range. */
        @JsonProperty("range_max") @Nullable Double rangeMax
    ) implements Range_a_B_ {}
}
