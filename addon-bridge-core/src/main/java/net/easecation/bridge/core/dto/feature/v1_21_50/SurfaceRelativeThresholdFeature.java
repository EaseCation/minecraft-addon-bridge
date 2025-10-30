package net.easecation.bridge.core.dto.feature.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* 'minecraft:surface<i>relative</i>threshold_feature' determines whether the provided position is below the estimated surface level of the world, and places a feature if so.If the provided position is above configured surface or the surface is not available, placement will fail. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SurfaceRelativeThresholdFeature(
    @JsonProperty("description") Description description,
    /* Named reference of feature to be placed. */
    @JsonProperty("feature_to_place") String featureToPlace,
    /* The minimum number of blocks required to be between the estimated surface level and a valid place for this feature. */
    @JsonProperty("minimum_distance_below_surface") @Nullable Integer minimumDistanceBelowSurface
) {
}
