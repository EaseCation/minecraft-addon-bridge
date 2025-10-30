package net.easecation.bridge.core.dto.feature_rule.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface ACoordDist {
    /* Expression for the coordinate (evaluated each iteration).  Mutually exclusive with random distribution object below. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record ACoordDist_Variant0(
    ) implements ACoordDist {
    }
    /* Distribution for the coordinate (evaluated each iteration).  Mutually exclusive with Molang expression above. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record ACoordDist_Variant1(
        /* UNDOCUMENTED. */
        @JsonProperty("numerator") @Nullable Double numerator,
        /* UNDOCUMENTED. */
        @JsonProperty("denominator") @Nullable Double denominator
    ) implements ACoordDist {
    }
}
