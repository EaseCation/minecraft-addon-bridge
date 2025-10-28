package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BCoordDist {
    /* Expression for the coordinate (evaluated each iteration).  Mutually exclusive with random distribution object below. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BCoordDist_Variant0(
    ) implements BCoordDist {}
    /* Distribution for the coordinate (evaluated each iteration).  Mutually exclusive with Molang expression above. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BCoordDist_Variant1(
        /* UNDOCUMENTED. */
        @JsonProperty("numerator") @Nullable Double numerator,
        /* UNDOCUMENTED. */
        @JsonProperty("denominator") @Nullable Double denominator
    ) implements BCoordDist {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BCoordDist_Variant2(
        /* Distribution type */
        @JsonProperty("distribution") @Nullable String distribution,
        /* Represents the range of values on which that distribution operates, from minimum to maximum. */
        @JsonProperty("extent") @Nullable List<Double> extent
    ) implements BCoordDist {}
}
