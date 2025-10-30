package net.easecation.bridge.core.dto.feature_rule.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FeatureRulesDefinition(
    @JsonProperty("format_version") @Nullable String formatVersion
) {
}
