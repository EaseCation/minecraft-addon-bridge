package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;

/* This component allows an entity to not spawn on a particular block. It includes a string or array of strings for the block they may not spawn on. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface SpawnsOnBlockPreventedFilter {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record SpawnsOnBlockPreventedFilter_Variant0(
        String value
    ) implements SpawnsOnBlockPreventedFilter {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record SpawnsOnBlockPreventedFilter_Variant1(
    ) implements SpawnsOnBlockPreventedFilter {}
}
