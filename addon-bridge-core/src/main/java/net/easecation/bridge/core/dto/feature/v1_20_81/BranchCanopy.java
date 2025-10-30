package net.easecation.bridge.core.dto.feature.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Configuration object for the canopy. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchCanopy(
    /* Configuration object for the acacia canopy. */
    @JsonProperty("acacia_canopy") @Nullable AcaciaCanopy acaciaCanopy,
    /* Configuration object for the normal canopy. */
    @JsonProperty("canopy") @Nullable Canopy canopy,
    /* Configuration object for the cherry canopy. */
    @JsonProperty("cherry_canopy") @Nullable CherryCanopy cherryCanopy,
    /* Configuration object for the fancy canopy. */
    @JsonProperty("fancy_canopy") @Nullable FancyCanopy fancyCanopy,
    /* Configuration object for the mangrove canopy. */
    @JsonProperty("mangrove_canopy") @Nullable MangroveCanopy mangroveCanopy,
    /* Configuration object for the mega canopy. */
    @JsonProperty("mega_canopy") @Nullable MegaCanopy megaCanopy,
    /* Configuration object for the mega pine canopy. */
    @JsonProperty("mega_pine_canopy") @Nullable MegaPineCanopy megaPineCanopy,
    /* Configuration object for the pine canopy. */
    @JsonProperty("pine_canopy") @Nullable PineCanopy pineCanopy,
    /* Configuration object for the roofed canopy. */
    @JsonProperty("roofed_canopy") @Nullable RoofedCanopy roofedCanopy,
    /* Configuration object for the spruce canopy. */
    @JsonProperty("spruce_canopy") @Nullable SpruceCanopy spruceCanopy
) {
    
        /* Configuration object for the acacia canopy. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record AcaciaCanopy(
            /* The size of the canopy. */
            @JsonProperty("canopy_size") @Nullable Integer canopySize,
            @JsonProperty("leaf_block") @Nullable String leafBlock,
            @JsonProperty("simplify_canopy") @Nullable Boolean simplifyCanopy
        ) {
        }
    
        /* Configuration object for the normal canopy. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Canopy(
            /* Canopy position offset relative to the block above the trunk. */
            @JsonProperty("canopy_offset") @Nullable CanopyOffset canopyOffset,
            /* Min width for the canopy. */
            @JsonProperty("min_width") @Nullable Integer minWidth,
            /* Configuration object for the canopy slope. */
            @JsonProperty("canopy_slope") @Nullable CanopySlope canopySlope,
            /* Determines the chance of creating leaf blocks for every layer of the canopy. Larger numbers create a denser tree. */
            @JsonProperty("variation_chance") @Nullable Object variationChance,
            @JsonProperty("leaf_block") @Nullable String leafBlock,
            /* Configuration object for the canopy decoration. */
            @JsonProperty("canopy_decoration") @Nullable CanopyDecoration canopyDecoration
        ) {
            
                /* Canopy position offset relative to the block above the trunk. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record CanopyOffset(
                    /* Min canopy position offset. */
                    @JsonProperty("min") @Nullable Integer min,
                    /* Max canopy position offset. */
                    @JsonProperty("max") @Nullable Integer max
                ) {
                }
            
                /* Configuration object for the canopy slope. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record CanopySlope(
                    /* The numerator for the slope fraction. */
                    @JsonProperty("rise") @Nullable Integer rise,
                    /* The denominator for the slope fraction. */
                    @JsonProperty("run") @Nullable Integer run
                ) {
                }
            
                /* Configuration object for the canopy decoration. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record CanopyDecoration(
                    /* Probability of decorating the trunk. */
                    @JsonProperty("decoration_chance") @Nullable ChanceInformation decorationChance,
                    /* The block used for decorating the trunk. */
                    @JsonProperty("decoration_block") @Nullable String decorationBlock,
                    /* Number of decoration blocks to place. */
                    @JsonProperty("num_steps") @Nullable Integer numSteps,
                    /* Directions to spread decoration blocks. */
                    @JsonProperty("step_direction") @Nullable String stepDirection
                ) {
                }
        }
    
        /* Configuration object for the cherry canopy. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record CherryCanopy(
            @JsonProperty("leaf_block") @Nullable String leafBlock,
            @JsonProperty("height") @Nullable Integer height,
            @JsonProperty("radius") @Nullable Integer radius,
            @JsonProperty("trunk_width") @Nullable Integer trunkWidth,
            /* Probability of the canopy having a hole in the bottom layer. */
            @JsonProperty("wide_bottom_layer_hole_chance") @Nullable ChanceInformation wideBottomLayerHoleChance,
            /* Probability of the canopy having a hole in the corner. */
            @JsonProperty("corner_hole_chance") @Nullable ChanceInformation cornerHoleChance,
            /* Probability of the canopy having hanging leaves */
            @JsonProperty("hanging_leaves_chance") @Nullable ChanceInformation hangingLeavesChance,
            /* Probability of hanging leaves extending further down */
            @JsonProperty("hanging_leaves_extension_chance") @Nullable ChanceInformation hangingLeavesExtensionChance
        ) {
        }
    
        /* Configuration object for the fancy canopy. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record FancyCanopy(
            @JsonProperty("height") @Nullable Integer height,
            @JsonProperty("radius") @Nullable Integer radius,
            @JsonProperty("leaf_block") @Nullable String leafBlock
        ) {
        }
    
        /* Configuration object for the mangrove canopy. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record MangroveCanopy(
            @JsonProperty("canopy_height") @Nullable Integer canopyHeight,
            @JsonProperty("canopy_radius") @Nullable Integer canopyRadius,
            /* Max number of attempts to create leaf blocks. */
            @JsonProperty("leaf_placement_attempts") @Nullable Integer leafPlacementAttempts,
            /* The blocks that form the canopy of the tree */
            @JsonProperty("leaf_blocks") @Nullable List<Object> leafBlocks,
            @JsonProperty("canopy_decoration") @Nullable Decoration canopyDecoration,
            /* The block to be used as a hanging block. */
            @JsonProperty("hanging_block") @Nullable String hangingBlock,
            /* Probability of creating a hanging leaf block. */
            @JsonProperty("hanging_block_placement_chance") @Nullable ChanceInformation hangingBlockPlacementChance
        ) {
        }
    
        /* Configuration object for the mega canopy. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record MegaCanopy(
            @JsonProperty("canopy_height") @Nullable Integer canopyHeight,
            @JsonProperty("base_radius") @Nullable Integer baseRadius,
            /* Width of the tree trunk. */
            @JsonProperty("core_width") @Nullable Double coreWidth,
            @JsonProperty("simplify_canopy") @Nullable Boolean simplifyCanopy,
            @JsonProperty("leaf_block") @Nullable String leafBlock
        ) {
        }
    
        /* Configuration object for the mega pine canopy. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record MegaPineCanopy(
            @JsonProperty("canopy_height") @Nullable Integer canopyHeight,
            @JsonProperty("base_radius") @Nullable Integer baseRadius,
            /* Modifier for the base radius of the canopy. */
            @JsonProperty("radius_step_modifier") @Nullable Double radiusStepModifier,
            /* Width of the tree trunk. */
            @JsonProperty("core_width") @Nullable Double coreWidth,
            @JsonProperty("leaf_block") @Nullable String leafBlock
        ) {
        }
    
        /* Configuration object for the pine canopy. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record PineCanopy(
            @JsonProperty("height") @Nullable Integer height,
            @JsonProperty("radius") @Nullable Integer radius,
            @JsonProperty("leaf_block") @Nullable String leafBlock
        ) {
        }
    
        /* Configuration object for the roofed canopy. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record RoofedCanopy(
            /* Roofed canopies feature a base and a top layer, and an extra cap layer on some occasions, this value controls the number of layers in the middle. */
            @JsonProperty("canopy_height") @Nullable Integer canopyHeight,
            /* Width of the tree trunk. */
            @JsonProperty("core_width") @Nullable Integer coreWidth,
            /* Radius used for the base and top layers. */
            @JsonProperty("outer_radius") @Nullable Integer outerRadius,
            /* Radius used for the middle layers. */
            @JsonProperty("inner_radius") @Nullable Integer innerRadius,
            @JsonProperty("leaf_block") @Nullable String leafBlock
        ) {
        }
    
        /* Configuration object for the spruce canopy. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record SpruceCanopy(
            /* Min canopy position offset. */
            @JsonProperty("lower_offset") @Nullable Integer lowerOffset,
            /* Max canopy position offset. */
            @JsonProperty("upper_offset") @Nullable Integer upperOffset,
            /* Max radius of the canopy. */
            @JsonProperty("max_radius") @Nullable Integer maxRadius,
            @JsonProperty("leaf_block") @Nullable String leafBlock
        ) {
        }
}
