package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BbGroupsSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BbGroupsSpec_Variant0(
        List<BbGroupsSpec> value
    ) implements BbGroupsSpec {
    }
}
