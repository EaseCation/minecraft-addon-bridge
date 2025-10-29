package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface FeatureRulesE {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record FeatureRulesE_Variant0(
        List<Object> value
    ) implements FeatureRulesE {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION) 
    record FeatureRulesE_Variant1(
    ) implements FeatureRulesE {
    }
}
