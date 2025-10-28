package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;

/* This component allows an entity to spawn on a particular block. It includes a string or array of strings for the block they may spawn on. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface SpawnsOnBlockFilter {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record SpawnsOnBlockFilter_Variant0(
        String value
    ) implements SpawnsOnBlockFilter {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record SpawnsOnBlockFilter_Variant1(
    ) implements SpawnsOnBlockFilter {}
}
