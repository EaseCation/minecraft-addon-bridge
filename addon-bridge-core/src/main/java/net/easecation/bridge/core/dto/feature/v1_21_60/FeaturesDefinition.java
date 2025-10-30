package net.easecation.bridge.core.dto.feature.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Features are decorations scattered throughout the world. Things such as trees, plants, flowers, springs, ore, and coral are all features. Basically, if it isn't the terrain or a mob, it's probably a feature! */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FeaturesDefinition(
    @JsonProperty("format_version") String formatVersion,
    @JsonProperty("minecraft:aggregate_feature") @Nullable AggregateFeature minecraft_aggregateFeature,
    @JsonProperty("minecraft:cave_carver_feature") @Nullable CaveCarverFeature minecraft_caveCarverFeature,
    @JsonProperty("minecraft:fossil_feature") @Nullable FossilFeature minecraft_fossilFeature,
    @JsonProperty("minecraft:geode_feature") @Nullable GeodeFeature minecraft_geodeFeature,
    @JsonProperty("minecraft:growing_plant_feature") @Nullable GrowingPlantFeature minecraft_growingPlantFeature,
    @JsonProperty("minecraft:multiface_feature") @Nullable MultifaceFeature minecraft_multifaceFeature,
    @JsonProperty("minecraft:nether_cave_carver_feature") @Nullable NetherCaveCarverFeature minecraft_netherCaveCarverFeature,
    @JsonProperty("minecraft:ore_feature") @Nullable OreFeature minecraft_oreFeature,
    @JsonProperty("minecraft:partially_exposed_blob_feature") @Nullable PartiallyExposedBlobFeature minecraft_partiallyExposedBlobFeature,
    @JsonProperty("minecraft:scatter_feature") @Nullable ScatterFeature minecraft_scatterFeature,
    @JsonProperty("minecraft:search_feature") @Nullable SearchFeature minecraft_searchFeature,
    @JsonProperty("minecraft:sequence_feature") @Nullable SequenceFeature minecraft_sequenceFeature,
    @JsonProperty("minecraft:single_block_feature") @Nullable SingleBlockFeature minecraft_singleBlockFeature,
    @JsonProperty("minecraft:snap_to_surface_feature") @Nullable SnapToSurfaceFeature minecraft_snapToSurfaceFeature,
    @JsonProperty("minecraft:structure_template_feature") @Nullable StructureTemplateFeature minecraft_structureTemplateFeature,
    @JsonProperty("minecraft:surface_relative_threshold_feature") @Nullable SurfaceRelativeThresholdFeature minecraft_surfaceRelativeThresholdFeature,
    @JsonProperty("minecraft:tree_feature") @Nullable TreeFeature minecraft_treeFeature,
    @JsonProperty("minecraft:underwater_cave_carver_feature") @Nullable UnderwaterCaveCarverFeature minecraft_underwaterCaveCarverFeature,
    @JsonProperty("minecraft:vegetation_patch_feature") @Nullable VegetationPatchFeature minecraft_vegetationPatchFeature,
    @JsonProperty("minecraft:weighted_random_feature") @Nullable WeightedRandomFeature minecraft_weightedRandomFeature
) {
}
