package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;

/* This component allows players to determine the herd size of animals. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface HeightFilter {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record HeightFilter_Variant1(
    ) implements HeightFilter {
    }
}
