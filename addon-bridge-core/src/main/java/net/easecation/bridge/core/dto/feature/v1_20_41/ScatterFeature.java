package net.easecation.bridge.core.dto.feature.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/*
 * {@code minecraft:scatter<i>feature} scatters a feature throughout a chunk. The {@code x}, {@code y}, and {@code z} fields are per-coordinate parameters.
 * Note that coordinates represent an offset from the input position, not an absolute position. Coordinates may be a single value, a random distribution, or molang expression that resolves to a numeric value. The {@code coordinate</i>eval<i>order} field is provided for finer control of coordinate resolution (particularly when using the {@code grid} distribution). {@code iterations} controls how many individual placements should occur if the {@code scatter</i>chance} check succeeds. The {@code scatter_chance} check happens once, so either all placements will run or none will.
 * Succeeds if: At least one feature placement succeeds.
 * Fails if: All feature placements fail.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ScatterFeature(
    /* UNDOCUMENTED. */
    @JsonProperty("description") Description description,
    /* Named reference of feature to be placed. */
    @JsonProperty("places_feature") String placesFeature,
    /* If true, snaps the y-value of the scattered position to the terrain heightmap. If false or unset, y-value is unmodified. */
    @JsonProperty("project_input_to_floor") @Nullable Boolean projectInputToFloor,
    /* Number of scattered positions to generate. */
    @JsonProperty("iterations") @Nullable MolangNumber iterations,
    @JsonProperty("scatter_chance") @Nullable Object scatterChance,
    /* The order in which coordinates will be evaluated. Should be used when a coordinate depends on another. If omitted, defaults to {@code xzy}. */
    @JsonProperty("coordinate_eval_order") @Nullable String coordinateEvalOrder,
    @JsonProperty("x") @Nullable Coordinate x,
    @JsonProperty("y") @Nullable Coordinate y,
    @JsonProperty("z") @Nullable Coordinate z
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
            @JsonProperty("identifier") String identifier
        ) {
        }
}
