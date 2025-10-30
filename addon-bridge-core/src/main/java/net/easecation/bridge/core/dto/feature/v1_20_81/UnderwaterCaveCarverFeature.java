package net.easecation.bridge.core.dto.feature.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/*
 * 'minecraft:underwater<i>cave</i>carver<i>feature' carves a cave through the world in the current chunk, and in every chunk around the current chunk in an 8 radial pattern.This feature will specifically target creating caves only below sea level.
 * This feature will also only work when placed specifically in the pass {@code pregeneration</i>pass}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UnderwaterCaveCarverFeature(
    @JsonProperty("description") Description description,
    /* Reference to the block to fill the cave with. */
    @JsonProperty("fill_with") @Nullable String fillWith,
    /* How many blocks to increase the cave radius by, from the center point of the cave. */
    @JsonProperty("width_modifier") @Nullable MolangNumber widthModifier,
    /* The chance to skip doing the carve (1 / value). */
    @JsonProperty("skip_carve_chance") @Nullable Integer skipCarveChance,
    /* The height limit where we attempt to carve */
    @JsonProperty("height_limit") @Nullable Integer heightLimit,
    /* The scaling in y */
    @JsonProperty("y_scale") @Nullable List<Double> yScale,
    @JsonProperty("horizontal_radius_multiplier") @Nullable List<Double> horizontalRadiusMultiplier,
    @JsonProperty("vertical_radius_multiplier") @Nullable List<Double> verticalRadiusMultiplier,
    @JsonProperty("floor_level") @Nullable List<Double> floorLevel,
    /* Reference to the block to replace air blocks with. */
    @JsonProperty("replace_air_with") @Nullable String replaceAirWith
) {
}
