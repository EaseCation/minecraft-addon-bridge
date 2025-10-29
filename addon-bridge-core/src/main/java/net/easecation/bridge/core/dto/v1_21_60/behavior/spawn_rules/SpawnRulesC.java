package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface SpawnRulesC {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record SpawnRulesC_Variant0(
        List<Object> value
    ) implements SpawnRulesC {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION) 
    record SpawnRulesC_Variant1(
    ) implements SpawnRulesC {
    }
}
