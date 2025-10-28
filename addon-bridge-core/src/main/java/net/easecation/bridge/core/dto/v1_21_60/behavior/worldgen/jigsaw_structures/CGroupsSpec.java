package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface CGroupsSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record CGroupsSpec_Variant0(
    ) implements CGroupsSpec {
    }
}
