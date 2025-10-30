package net.easecation.bridge.core.dto.feature_rule.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Each feature rule controls exactly one feature and serves as the root of a chain of feature data. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FeatureRules1130(
    /* Specifies the version of the game this entity was made in. Minimum supported version is 1.13.0. Current supported version is 1.13.0. */
    @JsonProperty("format_version") String formatVersion,
    /* Each feature rule controls exactly one feature and serves as the root of a chain of feature data. */
    @JsonProperty("minecraft:feature_rules") Minecraft_featureRules minecraft_featureRules
) {
    
        /* Each feature rule controls exactly one feature and serves as the root of a chain of feature data. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Minecraft_featureRules(
            /* UNDOCUMENTED. */
            @JsonProperty("description") Description description,
            /* Parameters to control where and when the feature will be placed. */
            @JsonProperty("conditions") Conditions conditions,
            /* Parameters controlling the initial scatter of the feature. */
            @JsonProperty("distribution") @Nullable Distribution distribution
        ) {
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Description(
                    /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
                    @JsonProperty("identifier") String identifier,
                    /* Named reference to the feature controlled by this rule. */
                    @JsonProperty("places_feature") String placesFeature
                ) {
                }
            
                /* Parameters to control where and when the feature will be placed. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Conditions(
                    /* When the feature should be placed relative to others. Earlier passes in the list are guaranteed to occur before later passes. Order is not guaranteed within each pass. */
                    @JsonProperty("placement_pass") String placementPass,
                    /* List of filter tests to determine which biomes this rule will attach to. */
                    @JsonProperty("minecraft:biome_filter") @Nullable Filters minecraft_biomeFilter
                ) {
                }
            
                /* Parameters controlling the initial scatter of the feature. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Distribution(
                    /* The order in which coordinates will be evaluated. Should be used when a coordinate depends on another. If omitted, defaults to {@code xzy}. */
                    @JsonProperty("coordinate_eval_order") @Nullable String coordinateEvalOrder,
                    /* Number of scattered positions to generate. */
                    @JsonProperty("iterations") MolangNumber iterations,
                    @JsonProperty("scatter_chance") @Nullable Object scatterChance,
                    @JsonProperty("x") @Nullable ACoordDist x,
                    @JsonProperty("z") @Nullable ACoordDist z,
                    @JsonProperty("y") @Nullable ACoordDist y
                ) {
                }
        }
}
