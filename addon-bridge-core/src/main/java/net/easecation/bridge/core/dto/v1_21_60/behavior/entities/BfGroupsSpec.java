package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BfGroupsSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BfGroupsSpec_Variant0(
    ) implements BfGroupsSpec {
    }
}
