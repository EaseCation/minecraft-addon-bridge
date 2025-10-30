package net.easecation.bridge.core.dto.feature_rule.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface DGroupsSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record DGroupsSpec_Variant0(
        List<DGroupsSpec> value
    ) implements DGroupsSpec {
    }
}
