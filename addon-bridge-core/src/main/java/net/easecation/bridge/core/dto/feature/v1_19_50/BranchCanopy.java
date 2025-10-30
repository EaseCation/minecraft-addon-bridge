package net.easecation.bridge.core.dto.feature.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchCanopy(
    /* UNDOCUMENTED. */
    @JsonProperty("acacia_canopy") @Nullable AcaciaCanopy acaciaCanopy,
    /* UNDOCUMENTED. */
    @JsonProperty("canopy") @Nullable Canopy canopy,
    /* UNDOCUMENTED. */
    @JsonProperty("fancy_canopy") @Nullable FancyCanopy fancyCanopy,
    /* UNDOCUMENTED. */
    @JsonProperty("mega_canopy") @Nullable MegaCanopy megaCanopy,
    /* UNDOCUMENTED. */
    @JsonProperty("mega_pine_canopy") @Nullable MegaPineCanopy megaPineCanopy,
    /* UNDOCUMENTED. */
    @JsonProperty("pine_canopy") @Nullable PineCanopy pineCanopy,
    /* UNDOCUMENTED. */
    @JsonProperty("roofed_canopy") @Nullable RoofedCanopy roofedCanopy,
    /* UNDOCUMENTED. */
    @JsonProperty("spruce_canopy") @Nullable SpruceCanopy spruceCanopy
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record AcaciaCanopy(
            /* UNDOCUMENTED. */
            @JsonProperty("canopy_size") @Nullable Integer canopySize,
            /* UNDOCUMENTED. */
            @JsonProperty("leaf_block") @Nullable String leafBlock,
            /* UNDOCUMENTED. */
            @JsonProperty("simplify_canopy") @Nullable Boolean simplifyCanopy
        ) {
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Canopy(
            /* UNDOCUMENTED. */
            @JsonProperty("canopy_offset") @Nullable CanopyOffset canopyOffset,
            /* UNDOCUMENTED. */
            @JsonProperty("min_width") @Nullable Integer minWidth,
            /* UNDOCUMENTED. */
            @JsonProperty("canopy_slope") @Nullable CanopySlope canopySlope,
            /* UNDOCUMENTED. */
            @JsonProperty("variation_chance") @Nullable Object variationChance,
            /* UNDOCUMENTED. */
            @JsonProperty("leaf_block") @Nullable String leafBlock,
            /* UNDOCUMENTED. */
            @JsonProperty("canopy_decoration") @Nullable CanopyDecoration canopyDecoration
        ) {
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record CanopyOffset(
                    /* UNDOCUMENTED. */
                    @JsonProperty("min") @Nullable Integer min,
                    /* UNDOCUMENTED. */
                    @JsonProperty("max") @Nullable Integer max
                ) {
                }
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record CanopySlope(
                    /* UNDOCUMENTED. */
                    @JsonProperty("rise") @Nullable Integer rise,
                    /* UNDOCUMENTED. */
                    @JsonProperty("run") @Nullable Integer run
                ) {
                }
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record CanopyDecoration(
                    /* UNDOCUMENTED. */
                    @JsonProperty("decoration_chance") @Nullable ChanceInformation decorationChance,
                    /* UNDOCUMENTED. */
                    @JsonProperty("decoration_block") @Nullable String decorationBlock,
                    /* UNDOCUMENTED. */
                    @JsonProperty("num_steps") @Nullable Integer numSteps,
                    /* UNDOCUMENTED. */
                    @JsonProperty("step_direction") @Nullable String stepDirection
                ) {
                }
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record FancyCanopy(
            /* UNDOCUMENTED. */
            @JsonProperty("height") @Nullable Integer height,
            /* UNDOCUMENTED. */
            @JsonProperty("radius") @Nullable Integer radius,
            /* UNDOCUMENTED. */
            @JsonProperty("leaf_block") @Nullable String leafBlock
        ) {
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record MegaCanopy(
            /* UNDOCUMENTED. */
            @JsonProperty("canopy_height") @Nullable Integer canopyHeight,
            /* UNDOCUMENTED. */
            @JsonProperty("base_radius") @Nullable Double baseRadius,
            /* UNDOCUMENTED. */
            @JsonProperty("core_width") @Nullable Double coreWidth,
            /* UNDOCUMENTED. */
            @JsonProperty("simplify_canopy") @Nullable Boolean simplifyCanopy,
            /* UNDOCUMENTED. */
            @JsonProperty("leaf_block") @Nullable String leafBlock
        ) {
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record MegaPineCanopy(
            /* UNDOCUMENTED. */
            @JsonProperty("canopy_height") @Nullable Integer canopyHeight,
            /* UNDOCUMENTED. */
            @JsonProperty("base_radius") @Nullable Integer baseRadius,
            /* UNDOCUMENTED. */
            @JsonProperty("radius_step_modifier") @Nullable Double radiusStepModifier,
            /* UNDOCUMENTED. */
            @JsonProperty("core_width") @Nullable Integer coreWidth,
            /* UNDOCUMENTED. */
            @JsonProperty("leaf_block") @Nullable String leafBlock
        ) {
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record PineCanopy(
            /* UNDOCUMENTED. */
            @JsonProperty("canopy_height") @Nullable Integer canopyHeight,
            /* UNDOCUMENTED. */
            @JsonProperty("base_radius") @Nullable Integer baseRadius,
            /* UNDOCUMENTED. */
            @JsonProperty("leaf_block") @Nullable String leafBlock
        ) {
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record RoofedCanopy(
            /* UNDOCUMENTED. */
            @JsonProperty("canopy_height") @Nullable Integer canopyHeight,
            /* UNDOCUMENTED. */
            @JsonProperty("core_width") @Nullable Integer coreWidth,
            /* UNDOCUMENTED. */
            @JsonProperty("outer_radius") @Nullable Integer outerRadius,
            /* UNDOCUMENTED. */
            @JsonProperty("inner_radius") @Nullable Integer innerRadius,
            /* UNDOCUMENTED. */
            @JsonProperty("leaf_block") @Nullable String leafBlock
        ) {
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record SpruceCanopy(
            /* UNDOCUMENTED. */
            @JsonProperty("lower_offset") @Nullable Integer lowerOffset,
            /* UNDOCUMENTED. */
            @JsonProperty("upper_offset") @Nullable Integer upperOffset,
            /* UNDOCUMENTED. */
            @JsonProperty("max_radius") @Nullable Integer maxRadius,
            /* UNDOCUMENTED. */
            @JsonProperty("leaf_block") @Nullable String leafBlock
        ) {
        }
}
