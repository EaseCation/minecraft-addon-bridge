package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Chance of something happening represented either as a fraction or a percentage. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface ChanceInformation {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record ChanceInformation_Variant0(
        /* UNDOCUMENTED. */
        @JsonProperty("numerator") @Nullable Double numerator,
        /* UNDOCUMENTED. */
        @JsonProperty("denominator") @Nullable Double denominator
    ) implements ChanceInformation {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record ChanceInformation_Variant1(
        Double value
    ) implements ChanceInformation {}
}
