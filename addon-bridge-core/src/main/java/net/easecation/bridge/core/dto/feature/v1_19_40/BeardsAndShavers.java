package net.easecation.bridge.core.dto.feature.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/*
 * {@code minecraft:beards<i>and</i>shavers} will build a {@code beard} or {@code shave} out space so as to provide a clear space for a feature to place.
 * Succeeds if: a beard/shave is made (this should always happen).
 * Fails if: will always return placement pos, but interior feature placement not guaranteed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BeardsAndShavers(
    /* UNDOCUMENTED. */
    @JsonProperty("description") Description description,
    /* Named reference of feature to be placed. */
    @JsonProperty("places_feature") String placesFeature,
    /* Dimensions of the Bounding Box. */
    @JsonProperty("bounding_box_min") List<Double> boundingBoxMin,
    /* Dimensions of the Bounding Box. */
    @JsonProperty("bounding_box_max") List<Double> boundingBoxMax,
    /* Y Delta for BAS. */
    @JsonProperty("y_delta") Double yDelta,
    /* Reference to the block to be placed. */
    @JsonProperty("surface_block_type") String surfaceBlockType,
    /* Reference to the block to be placed. */
    @JsonProperty("subsurface_block_type") String subsurfaceBlockType,
    /* Y Delta for BAS. */
    @JsonProperty("beard_raggedness_min") @Nullable Double beardRaggednessMin,
    /* Y Delta for BAS. */
    @JsonProperty("beard_raggedness_max") @Nullable Double beardRaggednessMax
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
            @JsonProperty("identifier") String identifier
        ) {
        }
}
