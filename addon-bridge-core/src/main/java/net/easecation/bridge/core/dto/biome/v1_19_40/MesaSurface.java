package net.easecation.bridge.core.dto.biome.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Similar to overworld_surface.  Adds colored strata and optional pillars. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MesaSurface(
    /* Controls the block type used for the surface of this biome. */
    @JsonProperty("top_material") @Nullable Object topMaterial,
    /* Controls the block type used in a layer below the surface of this biome. */
    @JsonProperty("mid_material") @Nullable Object midMaterial,
    /* Controls the block type used as a floor for bodies of water in this biome. */
    @JsonProperty("sea_floor_material") @Nullable Object seaFloorMaterial,
    /* Controls the block type used deep underground in this biome. */
    @JsonProperty("foundation_material") @Nullable Object foundationMaterial,
    /* Controls the block type used for the bodies of water in this biome. */
    @JsonProperty("sea_material") @Nullable Object seaMaterial,
    /* Controls how deep below the world water level the floor should occur. */
    @JsonProperty("sea_floor_depth") @Nullable Integer seaFloorDepth,
    /* UNDOCUMENTED. */
    @JsonProperty("clay_material") @Nullable String clayMaterial,
    /* UNDOCUMENTED. */
    @JsonProperty("hard_clay_material") @Nullable String hardClayMaterial,
    /* UNDOCUMENTED. */
    @JsonProperty("bryce_pillars") @Nullable Boolean brycePillars,
    /* UNDOCUMENTED. */
    @JsonProperty("has_forest") @Nullable Boolean hasForest
) {
}
