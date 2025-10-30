package net.easecation.bridge.core.dto.spawn_rule.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* This component allows the players to specify the permutations of a mob that will spawn. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface PermuteType {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record PermuteType_Variant1(
        List<PermuteType> value
    ) implements PermuteType {
    }
}
