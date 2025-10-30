package net.easecation.bridge.core.dto.feature_rule.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface EGroupsSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record EGroupsSpec_Variant0(
        List<EGroupsSpec> value
    ) implements EGroupsSpec {
    }
}
