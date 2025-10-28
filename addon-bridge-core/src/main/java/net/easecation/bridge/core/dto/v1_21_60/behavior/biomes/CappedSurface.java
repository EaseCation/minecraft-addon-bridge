package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Generates surface on blocks with non-solid blocks above or below. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CappedSurface(
    /* Materials used for the surface ceiling. */
    @JsonProperty("ceiling_materials") Object ceilingMaterials,
    /* Materials used for the surface floor. */
    @JsonProperty("floor_materials") Object floorMaterials,
    /* Material used to replace air blocks below sea level. */
    @JsonProperty("sea_material") BlockReference seaMaterial,
    /* Material used to repalce solid blocks that are not surface blocks. */
    @JsonProperty("foundation_material") BlockReference foundationMaterial,
    /* Material used to decorate surface near sea level. */
    @JsonProperty("beach_material") @Nullable BlockReference beachMaterial
) {
}
