package net.easecation.bridge.core.dto.spawn_rule.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface HeightFilter {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record HeightFilter_Variant1(
        List<Herd> value
    ) implements HeightFilter {
    }
}
