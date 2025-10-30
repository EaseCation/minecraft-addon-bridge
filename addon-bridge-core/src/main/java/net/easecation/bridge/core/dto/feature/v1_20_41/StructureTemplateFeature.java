package net.easecation.bridge.core.dto.feature.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Feature type 'minecraft:tree_feature' has not yet been documented. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StructureTemplateFeature(
    /* UNDOCUMENTED. */
    @JsonProperty("description") Description description,
    @JsonProperty("base_block") @Nullable Object baseBlock,
    /* UNDOCUMENTED. */
    @JsonProperty("base_cluster") @Nullable BaseCluster baseCluster,
    /* UNDOCUMENTED. */
    @JsonProperty("may_grow_on") @Nullable List<String> mayGrowOn,
    /* UNDOCUMENTED. */
    @JsonProperty("may_replace") @Nullable List<String> mayReplace,
    /* UNDOCUMENTED. */
    @JsonProperty("may_grow_through") @Nullable List<String> mayGrowThrough,
    /* UNDOCUMENTED. */
    @JsonProperty("acacia_trunk") @Nullable AcaciaTrunk acaciaTrunk,
    /* UNDOCUMENTED. */
    @JsonProperty("fallen_trunk") @Nullable FallenTrunk fallenTrunk,
    /* UNDOCUMENTED. */
    @JsonProperty("fancy_trunk") @Nullable FancyTrunk fancyTrunk,
    /* UNDOCUMENTED. */
    @JsonProperty("mega_trunk") @Nullable MegaTrunk megaTrunk,
    /* UNDOCUMENTED. */
    @JsonProperty("trunk") @Nullable Trunk trunk,
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
    @JsonProperty("spruce_canopy") @Nullable SpruceCanopy spruceCanopy,
    /* UNDOCUMENTED. */
    @JsonProperty("random_spread_canopy") @Nullable RandomSpreadCanopy randomSpreadCanopy
) {
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Description(
            /* The name of this feature in the form {@code namespace<i>name:feature</i>name}. {@code feature_name} must match the filename. */
            @JsonProperty("identifier") String identifier
        ) {
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record BaseCluster(
            /* UNDOCUMENTED. */
            @JsonProperty("may_replace") List<String> mayReplace,
            /* UNDOCUMENTED. */
            @JsonProperty("num_clusters") Integer numClusters,
            /* UNDOCUMENTED. */
            @JsonProperty("cluster_radius") Integer clusterRadius
        ) {
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record AcaciaTrunk(
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_width") @Nullable Integer trunkWidth,
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_height") @Nullable TrunkHeight trunkHeight,
            @JsonProperty("trunk_lean") @Nullable Object trunkLean,
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_block") @Nullable String trunkBlock,
            /* UNDOCUMENTED. */
            @JsonProperty("branches") @Nullable Branches branches
        ) {
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record TrunkHeight(
                    /* UNDOCUMENTED. */
                    @JsonProperty("base") @Nullable Integer base,
                    /* UNDOCUMENTED. */
                    @JsonProperty("intervals") @Nullable List<Integer> intervals,
                    /* UNDOCUMENTED. */
                    @JsonProperty("min_height_for_canopy") @Nullable Integer minHeightForCanopy
                ) {
                }
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Branches(
                    /* UNDOCUMENTED. */
                    @JsonProperty("branch_length") @Nullable Double branchLength,
                    /* UNDOCUMENTED. */
                    @JsonProperty("branch_position") @Nullable Double branchPosition,
                    /* UNDOCUMENTED. */
                    @JsonProperty("branch_chance") @Nullable ChanceInformation branchChance,
                    /* UNDOCUMENTED. */
                    @JsonProperty("branch_canopy") @Nullable BranchCanopy branchCanopy,
                    /* UNDOCUMENTED. */
                    @JsonProperty("trunk_decoration") @Nullable TrunkDecoration trunkDecoration
                ) {
                    
                        /* UNDOCUMENTED. */
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public record TrunkDecoration(
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
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record FallenTrunk(
            /* UNDOCUMENTED. */
            @JsonProperty("log_length") @Nullable Integer logLength,
            /* UNDOCUMENTED. */
            @JsonProperty("stump_height") @Nullable Integer stumpHeight,
            /* UNDOCUMENTED. */
            @JsonProperty("height_modifier") @Nullable Integer heightModifier,
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_block") @Nullable String trunkBlock,
            /* UNDOCUMENTED. */
            @JsonProperty("log_decoration_feature") @Nullable String logDecorationFeature,
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_decoration") @Nullable TrunkDecoration trunkDecoration
        ) {
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record TrunkDecoration(
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
        public record FancyTrunk(
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_height") @Nullable TrunkHeight trunkHeight,
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_width") @Nullable Integer trunkWidth,
            /* UNDOCUMENTED. */
            @JsonProperty("branches") @Nullable Branches branches,
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_block") @Nullable String trunkBlock,
            /* UNDOCUMENTED. */
            @JsonProperty("width_scale") @Nullable Double widthScale,
            /* UNDOCUMENTED. */
            @JsonProperty("foliage_altitude_factor") @Nullable Double foliageAltitudeFactor
        ) {
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record TrunkHeight(
                    /* UNDOCUMENTED. */
                    @JsonProperty("base") @Nullable Integer base,
                    /* UNDOCUMENTED. */
                    @JsonProperty("variance") @Nullable Integer variance,
                    /* UNDOCUMENTED. */
                    @JsonProperty("scale") @Nullable Double scale
                ) {
                }
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Branches(
                    /* UNDOCUMENTED. */
                    @JsonProperty("slope") @Nullable Double slope,
                    /* UNDOCUMENTED. */
                    @JsonProperty("density") @Nullable Double density,
                    /* UNDOCUMENTED. */
                    @JsonProperty("min_altitude_factor") @Nullable Double minAltitudeFactor
                ) {
                }
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record MegaTrunk(
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_width") @Nullable Integer trunkWidth,
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_height") @Nullable TrunkHeight trunkHeight,
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_block") @Nullable String trunkBlock,
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_decoration") @Nullable TrunkDecoration trunkDecoration,
            /* UNDOCUMENTED. */
            @JsonProperty("branches") @Nullable Branches branches
        ) {
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record TrunkHeight(
                    /* UNDOCUMENTED. */
                    @JsonProperty("base") @Nullable Integer base,
                    /* UNDOCUMENTED. */
                    @JsonProperty("intervals") @Nullable List<Integer> intervals
                ) {
                }
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record TrunkDecoration(
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
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Branches(
                    /* UNDOCUMENTED. */
                    @JsonProperty("branch_length") @Nullable Integer branchLength,
                    /* UNDOCUMENTED. */
                    @JsonProperty("branch_slope") @Nullable Double branchSlope,
                    /* UNDOCUMENTED. */
                    @JsonProperty("branch_interval") @Nullable Integer branchInterval,
                    /* UNDOCUMENTED. */
                    @JsonProperty("branch_altitude_factor") @Nullable BranchAltitudeFactor branchAltitudeFactor,
                    /* UNDOCUMENTED. */
                    @JsonProperty("branch_canopy") @Nullable BranchCanopy branchCanopy
                ) {
                    
                        /* UNDOCUMENTED. */
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public record BranchAltitudeFactor(
                            /* UNDOCUMENTED. */
                            @JsonProperty("min") @Nullable Double min,
                            /* UNDOCUMENTED. */
                            @JsonProperty("max") @Nullable Double max
                        ) {
                        }
                }
        }
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Trunk(
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_height") @Nullable Integer trunkHeight,
            /* UNDOCUMENTED. */
            @JsonProperty("height_modifier") @Nullable Integer heightModifier,
            /* UNDOCUMENTED. */
            @JsonProperty("can_be_submerged") @Nullable Object canBeSubmerged,
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_block") @Nullable String trunkBlock,
            /* UNDOCUMENTED. */
            @JsonProperty("trunk_decoration") @Nullable TrunkDecoration trunkDecoration
        ) {
            
                /* UNDOCUMENTED. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record TrunkDecoration(
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
    
        /* UNDOCUMENTED. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record RandomSpreadCanopy(
            /* UNDOCUMENTED. */
            @JsonProperty("canopy_height") @Nullable Integer canopyHeight,
            /* UNDOCUMENTED. */
            @JsonProperty("canopy_radius") @Nullable Integer canopyRadius,
            /* UNDOCUMENTED. */
            @JsonProperty("leaf_placement_attempts") @Nullable Integer leafPlacementAttempts,
            /* UNDOCUMENTED. */
            @JsonProperty("leaf_blocks") @Nullable List<List<Object>> leafBlocks
        ) {
        }
}
