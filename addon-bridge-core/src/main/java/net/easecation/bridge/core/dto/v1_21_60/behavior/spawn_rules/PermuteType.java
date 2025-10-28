package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;

/* This component allows the players to specify the permutations of a mob that will spawn. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface PermuteType {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record PermuteType_Variant1(
    ) implements PermuteType {}
}
