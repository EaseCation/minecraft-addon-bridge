package net.easecation.bridge.core.dto.spawn_rule.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface CGroupsSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record CGroupsSpec_Variant0(
        List<CGroupsSpec> value
    ) implements CGroupsSpec {
    }
}
