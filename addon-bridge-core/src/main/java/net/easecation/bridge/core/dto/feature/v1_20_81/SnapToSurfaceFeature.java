package net.easecation.bridge.core.dto.feature.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/*
 * {@code minecraft:snap<i>to</i>surface<i>feature} snaps the y-value of a feature placement pos to the floor or the ceiling within the provided {@code vertical</i>search_range}. The placement biome is preserved. 
 * If the snap position goes outside of the placement biome, placement will fail.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SnapToSurfaceFeature(
    @JsonProperty("description") Description description,
    /* Named reference of feature to be snapped. */
    @JsonProperty("feature_to_snap") String featureToSnap,
    /* Range to search for a floor or ceiling for snaping the feature. */
    @JsonProperty("vertical_search_range") Double verticalSearchRange,
    /* Defines the surface that the y-value of the placement position will be snapped to. Valid values: {@code ceiling} and `floor' */
    @JsonProperty("surface") @Nullable String surface,
    /* Determines whether the feature can snap through air blocks */
    @JsonProperty("allow_air_placement") @Nullable Boolean allowAirPlacement,
    /* Determines whether the feature can snap through water blocks */
    @JsonProperty("allow_underwater_placement") @Nullable Boolean allowUnderwaterPlacement
) {
}
