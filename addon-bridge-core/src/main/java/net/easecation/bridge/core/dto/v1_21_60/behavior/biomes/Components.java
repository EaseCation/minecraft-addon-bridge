package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Components(
    @JsonProperty("minecraft:capped_surface") @Nullable CappedSurface minecraft_cappedSurface,
    @JsonProperty("minecraft:climate") @Nullable Climate minecraft_climate,
    @JsonProperty("minecraft:creature_spawn_probability") @Nullable CreatureSpawnProbability minecraft_creatureSpawnProbability,
    @JsonProperty("minecraft:frozen_ocean_surface") @Nullable FrozenOceanSurface minecraft_frozenOceanSurface,
    @JsonProperty("minecraft:mesa_surface") @Nullable MesaSurface minecraft_mesaSurface,
    @JsonProperty("minecraft:mountain_parameters") @Nullable MountainParameters minecraft_mountainParameters,
    @JsonProperty("minecraft:multinoise_generation_rules") @Nullable NetherGenerationRules minecraft_multinoiseGenerationRules,
    @JsonProperty("minecraft:overworld_generation_rules") @Nullable OverworldGenerationRules minecraft_overworldGenerationRules,
    @JsonProperty("minecraft:overworld_height") @Nullable OverworldHeight minecraft_overworldHeight,
    @JsonProperty("minecraft:replace_biomes") @Nullable ReplaceBiomes minecraft_replaceBiomes,
    @JsonProperty("minecraft:surface_material_adjustments") @Nullable SurfaceMaterialAdjustments minecraft_surfaceMaterialAdjustments,
    @JsonProperty("minecraft:surface_parameters") @Nullable SurfaceParameters minecraft_surfaceParameters,
    @JsonProperty("minecraft:swamp_surface") @Nullable SwampSurface minecraft_swampSurface,
    @JsonProperty("minecraft:tags") @Nullable Tags minecraft_tags,
    @JsonProperty("minecraft:the_end_surface") @Nullable net.easecation.bridge.core.dto.EmptyObject minecraft_theEndSurface
) {
}
