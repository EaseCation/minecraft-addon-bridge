package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;

/* Similar to overworld_surface. Adds colored strata and optional pillars. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MesaSurface(
    /* Controls the block type used for the surface of this biome. */
    @JsonProperty("top_material") BlockReference topMaterial,
    /* Controls the block type used in a layer below the surface of this biome. */
    @JsonProperty("mid_material") BlockReference midMaterial,
    /* Controls the block type used as a floor for bodies of water in this biome. */
    @JsonProperty("sea_floor_material") BlockReference seaFloorMaterial,
    /* Controls the block type used deep underground in this biome. */
    @JsonProperty("foundation_material") BlockReference foundationMaterial,
    /* Controls the block type used for the bodies of water in this biome. */
    @JsonProperty("sea_material") BlockReference seaMaterial,
    /* Controls how deep below the world water level the floor should occur. */
    @JsonProperty("sea_floor_depth") Integer seaFloorDepth,
    /* Base clay block to use. */
    @JsonProperty("clay_material") BlockReference clayMaterial,
    /* Hardened clay block to use. */
    @JsonProperty("hard_clay_material") BlockReference hardClayMaterial,
    /* Whether the mesa generates with pillars. */
    @JsonProperty("bryce_pillars") Boolean brycePillars,
    /* Places coarse dirt and grass at high altitudes. */
    @JsonProperty("has_forest") Boolean hasForest
) {
}
