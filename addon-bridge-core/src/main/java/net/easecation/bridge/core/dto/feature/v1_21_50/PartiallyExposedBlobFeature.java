package net.easecation.bridge.core.dto.feature.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* 'minecraft:partially<i>exposed</i>blob_feature' generates a blob of the specified block with the specified dimensions For the most part the blob is embedded in the specified surface, however a single side is allowed to be exposed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PartiallyExposedBlobFeature(
    @JsonProperty("description") Description description,
    /* Reference to the block to be placed. */
    @JsonProperty("places_block") String placesBlock,
    /* Defines the cubic radius of the blob. */
    @JsonProperty("placement_radius_around_floor") Double placementRadiusAroundFloor,
    /* The probability of trying to place a block at each position within the placement bounds. */
    @JsonProperty("placement_probability_per_valid_position") Double placementProbabilityPerValidPosition,
    /* Defines a block face that is allowed to be exposed to air and/or water. Other faces need to be embedded for blocks to be placed by this feature. Defaults to upwards face */
    @JsonProperty("exposed_face") @Nullable String exposedFace
) {
}
