package net.easecation.bridge.core.dto.spawn_rule.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* This component allows players to determine the herd size of animals. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface HeightFilter {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record HeightFilter_Variant1(
        List<Herd> value
    ) implements HeightFilter {
    }
}
