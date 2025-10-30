package net.easecation.bridge.core.dto.feature_rule.v1_21_60;

import com.fasterxml.jackson.annotation.*;

/* Each feature rule controls exactly one feature and serves as the root of a chain of feature data. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FeatureRulesDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:feature_rules") FeatureRules minecraft_featureRules
) {
}
