package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BfGroupsSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BfGroupsSpec_Variant0(
        List<BfGroupsSpec> value
    ) implements BfGroupsSpec {
    }
}
