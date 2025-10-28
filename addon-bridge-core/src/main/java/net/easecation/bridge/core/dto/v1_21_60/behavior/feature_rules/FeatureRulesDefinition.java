package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;

/* Each feature rule controls exactly one feature and serves as the root of a chain of feature data. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FeatureRulesDefinition(
    @JsonProperty("format_version") FormatVersion formatVersion,
    @JsonProperty("minecraft:feature_rules") FeatureRules minecraft_featureRules
) {
}
