package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BgGroupsSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BgGroupsSpec_Variant0(
        List<BgGroupsSpec> value
    ) implements BgGroupsSpec {
    }
}
