package net.easecation.bridge.core.dto.spawn_rule.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BiomeFilter {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BiomeFilter_Variant0(
        List<Filters> value
    ) implements BiomeFilter {
    }
}
