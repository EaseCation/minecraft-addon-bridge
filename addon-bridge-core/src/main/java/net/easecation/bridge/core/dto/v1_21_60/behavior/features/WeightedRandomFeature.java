package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

/*
 * 'minecraft:weighted<i>random</i>feature' randomly selects and places a feature based on a weight value. Weights are relative, with higher values making selection more likely.
 * Succeeds if: The selected feature is placed.
 * Fails if: The selected feature fails to be placed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WeightedRandomFeature(
    @JsonProperty("description") Description description,
    /* Collection of weighted features that placement will select from. */
    @JsonProperty("features") List<List<Object>> features
) {
}
