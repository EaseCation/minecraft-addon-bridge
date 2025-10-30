package net.easecation.bridge.core.dto.feature.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Feature type {@code minecraft:vegetation<i>patch</i>feature} has not yet been documented. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VegetationPatchFeature(
    @JsonProperty("description") Description description,
    /* Blocks that can be replaced by the ground blocks on the patch. */
    @JsonProperty("replaceable_blocks") List<String> replaceableBlocks,
    /* Block used to create a base for the vegetation patch. */
    @JsonProperty("ground_block") String groundBlock,
    /* Feature that will be placed by the patch. */
    @JsonProperty("vegetation_feature") String vegetationFeature,
    /* Determines if a vegetation patch will grow from the ceiling or the floor. */
    @JsonProperty("surface") @Nullable String surface,
    /* Depth of the base covered by the ground blocks. */
    @JsonProperty("depth") @Nullable Integer depth,
    /* Probability of putting the ground blocks one block deeper. Adds some randomness to the bottom of the patch. */
    @JsonProperty("extra_deep_block_chance") @Nullable Double extraDeepBlockChance,
    /* Vertical range used to determine a suitable surface position for the patch. */
    @JsonProperty("vertical_range") @Nullable Integer verticalRange,
    /* Probability of spawning vegetation on the patch. Larger numbers create a denser vegetation patch. */
    @JsonProperty("vegetation_chance") @Nullable Double vegetationChance,
    /* Horizontal area that the vegetation patch will cover. */
    @JsonProperty("horizontal_radius") Integer horizontalRadius,
    /* Probability of spawning vegetation on the edge of the patch radius. */
    @JsonProperty("extra_edge_column_chance") @Nullable Double extraEdgeColumnChance,
    /* If true, waterlogs the positions occupied by the ground blocks. */
    @JsonProperty("waterlogged") @Nullable Boolean waterlogged
) {
}
