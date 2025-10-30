package net.easecation.bridge.core.dto.feature.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/*
 * 'minecraft:weighted<i>random</i>feature' randomly selects and places a feature based on a weight value. Weights are relative, with higher values making selection more likely.
 * Succeeds if: The selected feature is placed.
 * Fails if: The selected feature fails to be placed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WeightedRandomFeature(
    /* UNDOCUMENTED. */
    @JsonProperty("description") Description description,
    /* Collection of weighted features that placement will select from. */
    @JsonProperty("features") List<List<Object>> features
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
            @JsonProperty("identifier") String identifier
        ) {
        }
}
