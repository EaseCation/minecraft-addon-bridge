package net.easecation.bridge.core.dto.biome.v1_21_50;

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
    @JsonProperty("sea_material") String seaMaterial,
    /* Material used to repalce solid blocks that are not surface blocks. */
    @JsonProperty("foundation_material") String foundationMaterial,
    /* Material used to decorate surface near sea level. */
    @JsonProperty("beach_material") @Nullable String beachMaterial
) {
}
