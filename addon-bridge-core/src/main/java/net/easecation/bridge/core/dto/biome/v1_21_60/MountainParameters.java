package net.easecation.bridge.core.dto.biome.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Noise parameters used to drive mountain terrain generation in Overworld. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MountainParameters(
    /* UNDOCUMENTED. */
    @JsonProperty("peaks_factor") @Nullable Double peaksFactor,
    /* Defines surface material for steep slopes. */
    @JsonProperty("steep_material_adjustment") @Nullable SteepMaterialAdjustment steepMaterialAdjustment,
    /* Controls the density tapering that happens at the top of the world to prevent terrain from reaching too high. */
    @JsonProperty("top_slide") @Nullable TopSlide topSlide
) {
    
        /* Defines surface material for steep slopes. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record SteepMaterialAdjustment(
            /* Block type use as steep material. */
            @JsonProperty("material") @Nullable String material,
            /* Enable for north facing slopes. */
            @JsonProperty("north_slopes") @Nullable Boolean northSlopes,
            /* Enable for south facing slopes. */
            @JsonProperty("south_slopes") @Nullable Boolean southSlopes,
            /* Enable for west facing slopes. */
            @JsonProperty("west_slopes") @Nullable Boolean westSlopes,
            /* Enable for east facing slopes. */
            @JsonProperty("east_slopes") @Nullable Boolean eastSlopes
        ) {
        }
    
        /* Controls the density tapering that happens at the top of the world to prevent terrain from reaching too high. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record TopSlide(
            /* If false, top slide will be disabled. If true, other parameters will be taken into account */
            @JsonProperty("enabled") @Nullable Object enabled
        ) {
        }
}
