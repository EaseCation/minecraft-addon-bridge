package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface BeGroupsSpec {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record BeGroupsSpec_Variant0(
        List<BeGroupsSpec> value
    ) implements BeGroupsSpec {
    }
}
