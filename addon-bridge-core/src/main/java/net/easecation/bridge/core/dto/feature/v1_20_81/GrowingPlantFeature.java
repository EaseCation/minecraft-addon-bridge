package net.easecation.bridge.core.dto.feature.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/*
 * {@code minecraft:growing<i>plant</i>feature} places a growing plant in the world. A growing plant is a column that is anchored either to the ceiling or the floor, based on its growth direction.
 * The growing plant has a body and a head, where the head is the tip of the plant, and the body consists of the remainder blocks.
 * This feature can be used to define growing plants with variable body and head blocks, e.g. Cave Vines.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GrowingPlantFeature(
    @JsonProperty("description") Description description,
    /* Age of the head of the plant. */
    @JsonProperty("age") @Nullable JRangeorint age,
    /* Collection of weighted heights that placement will select from. */
    @JsonProperty("height_distribution") List<List<Object>> heightDistribution,
    /* Direction that the plant grows towards. Valid values: UP and DOWN */
    @JsonProperty("growth_direction") String growthDirection,
    /* Collection of weighted block descriptor that placement will select from for the body of the plant. */
    @JsonProperty("body_blocks") JBlocksArray bodyBlocks,
    /* Collection of weighted block descriptor that placement will select from for the body of the plant. */
    @JsonProperty("head_blocks") JBlocksArray headBlocks,
    /* Plant blocks can be placed in water. */
    @JsonProperty("allow_water") @Nullable Boolean allowWater
) {
}
