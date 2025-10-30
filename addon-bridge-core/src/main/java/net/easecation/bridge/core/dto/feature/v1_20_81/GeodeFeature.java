package net.easecation.bridge.core.dto.feature.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/*
 * {@code minecraft:geode_feature} generates a rock formation to simulate a geode. Each layer of, and block within, the geode can be replaced.
 * Succeeds if: At least one block within the geode is placed.
 * Fails if: All blocks within the geode fail to be placed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GeodeFeature(
    @JsonProperty("description") Description description,
    /* The block to fill the inside of the geode. */
    @JsonProperty("filler") String filler,
    /* The block that forms the inside layer of the geode shell. */
    @JsonProperty("inner_layer") String innerLayer,
    /* The block that has a chance of generating instead of inner_layer. */
    @JsonProperty("alternate_inner_layer") String alternateInnerLayer,
    /* The block that forms the middle layer of the geode shell. */
    @JsonProperty("middle_layer") String middleLayer,
    /* The block that forms the outer shell of the geode. */
    @JsonProperty("outer_layer") String outerLayer,
    /* A list of blocks that may be replaced during placement. Omit this field to allow any block to be replaced. */
    @JsonProperty("inner_placements") @Nullable List<String> innerPlacements,
    /* The minimum distance each distribution point must be from the outer wall. [0,10] */
    @JsonProperty("min_outer_wall_distance") Integer minOuterWallDistance,
    /* The maximum distance each distribution point can be from the outer wall. [0,20] */
    @JsonProperty("max_outer_wall_distance") Integer maxOuterWallDistance,
    /* The minimum number of points inside the distance field that can get generated. The distance field is the area consisting of all points with a minimum distance to all destribution points. [0,10] */
    @JsonProperty("min_distribution_points") Integer minDistributionPoints,
    /* The maximum number of points inside the distance field that can get generated. The distance field is the area consisting of all points with a minimum distance to all destribution points. [0,20] */
    @JsonProperty("max_distribution_points") Integer maxDistributionPoints,
    /* The lowest possible value of random offset applied to the position of each distribution point. [0,10] */
    @JsonProperty("min_point_offset") Integer minPointOffset,
    /* The highest possible value of random offset applied to the position of each distribution point. [0,10] */
    @JsonProperty("max_point_offset") Integer maxPointOffset,
    /* The maximum possible radius of the geode generated. */
    @JsonProperty("max_radius") Integer maxRadius,
    /* An offset applied to each distribution point that forms the geode crack opening. [0,10] */
    @JsonProperty("crack_point_offset") Integer crackPointOffset,
    /* The likelihood of a geode generating with a crack in its shell. [0,1] */
    @JsonProperty("generate_crack_chance") Double generateCrackChance,
    /* How large the crack opening of the geode should be when generated. [0,5] */
    @JsonProperty("base_crack_size") Double baseCrackSize,
    /* A multiplier applied to the noise that is applied to the distribution points within the geode. Higher = more noisy. */
    @JsonProperty("noise_multiplier") Double noiseMultiplier,
    /* The likelihood that a special block will be placed on the inside of the geode. [0,1] */
    @JsonProperty("use_potential_placements_chance") Double usePotentialPlacementsChance,
    /* The likelihood that a block in the innermost layer of the geode will be replaced with an alternate option. [0,1] */
    @JsonProperty("use_alternate_layer0_chance") Double useAlternateLayer0Chance,
    /* If true, the potential placement block will only be placed on the alternate layer0 blocks that get placed. Potential placement blocks are blocks that depend on the existance of another block to be placed. The latter are the layer0 alternate blocks. */
    @JsonProperty("placements_require_layer0_alternate") Boolean placementsRequireLayer0Alternate,
    /* The threshold of invalid blocks for a geode to have a distribution point in before it aborts generation entirely. */
    @JsonProperty("invalid_blocks_threshold") Integer invalidBlocksThreshold
) {
}
