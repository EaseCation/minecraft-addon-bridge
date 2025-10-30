package net.easecation.bridge.core.dto.feature.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/*
 * {@code minecraft:search<i>feature} sweeps a volume searching for a valid placement location for its referenced feature. The {@code search</i>volume} field specifies the axis-aligned bounding box that defines the boundaries of the search. The search sweeps along the axis defined by the {@code search<i>axis} field, layer by layer. For example, if {@code search</i>axis} = '-x', blocks with greater x values will be checked before blocks with lower x values. Each layer is searched from the bottom-left to the top-right before moving to the next layer along the axis. By default, only one valid position must be found, but this can be altered by specifying the {@code required<i>successes} field. If fewer than the required successes are found, no placement will occur.
 * Succeeds if: The number of valid positions is equal to the value specified by {@code required</i>successes}.
 * Fails if: The number of valid positions is less than the value specified by {@code required_successes}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchFeature(
    @JsonProperty("description") Description description,
    /* Named reference of feature to be placed. */
    @JsonProperty("places_feature") @Nullable String placesFeature,
    /* Axis-aligned bounding box that will be searched for valid placement positions. Expressed as offsets from the input position. */
    @JsonProperty("search_volume") @Nullable SearchVolume searchVolume,
    /* Axis that the search will sweep along through the {@code search_volume}. */
    @JsonProperty("search_axis") String searchAxis,
    /* Number of valid positions the search must find in order to place the referenced feature. */
    @JsonProperty("required_successes") @Nullable Integer requiredSuccesses
) {
    
        /* Axis-aligned bounding box that will be searched for valid placement positions. Expressed as offsets from the input position. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record SearchVolume(
            /* Maximum extent of the bounding volume expressed as [ x, y, z ]. */
            @JsonProperty("max") List<Integer> max,
            /* Maxium extent of the bounding volume expressed as [ x, y, z ]. */
            @JsonProperty("min") List<Integer> min
        ) {
        }
}
