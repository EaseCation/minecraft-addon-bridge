package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface EGroupsSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record EGroupsSpec_Variant0(
    ) implements EGroupsSpec {
    }
}
