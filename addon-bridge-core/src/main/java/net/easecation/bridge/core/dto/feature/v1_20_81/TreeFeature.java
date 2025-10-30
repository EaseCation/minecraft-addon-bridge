package net.easecation.bridge.core.dto.feature.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* 'minecraft:tree_feature' will place a tree in the world. A tree consists of a column that is anchored to a base block with set parameters for what it can be placed on and canopy that extends from the column. Trees support multiple types of canopies, trunks, and roots. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TreeFeature(
    @JsonProperty("description") Description description,
    @JsonProperty("base_block") @Nullable Object baseBlock,
    /* Allows you to define a number of clusters for the base of the tree. Used to generate mega tree variants. */
    @JsonProperty("base_cluster") @Nullable BaseCluster baseCluster,
    /* List of blocks where a tree can grow on. */
    @JsonProperty("may_grow_on") @Nullable List<String> mayGrowOn,
    /* List of blocks that a tree can replace. */
    @JsonProperty("may_replace") @Nullable List<String> mayReplace,
    /* List of blocks that a tree can grow through. */
    @JsonProperty("may_grow_through") @Nullable List<String> mayGrowThrough,
    /* Configutarion for the acacia trunk. */
    @JsonProperty("acacia_trunk") @Nullable AcaciaTrunk acaciaTrunk,
    /* Configutarion for the cherry trunk. */
    @JsonProperty("cherry_trunk") @Nullable CherryTrunk cherryTrunk,
    /* Configutarion for the fallen trunk. */
    @JsonProperty("fallen_trunk") @Nullable FallenTrunk fallenTrunk,
    /* Configutarion for the fancy trunk. */
    @JsonProperty("fancy_trunk") @Nullable FancyTrunk fancyTrunk,
    /* Configutarion for the mangrove trunk. */
    @JsonProperty("mangrove_trunk") @Nullable MangroveTrunk mangroveTrunk,
    /* Configutarion for the mega trunk. */
    @JsonProperty("mega_trunk") @Nullable MegaTrunk megaTrunk,
    /* Configutarion for the normal trunk. */
    @JsonProperty("trunk") @Nullable Trunk trunk,
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
    @JsonProperty("spruce_canopy") @Nullable SpruceCanopy spruceCanopy,
    /* Configuration object for the random spread canopy. */
    @JsonProperty("random_spread_canopy") @Nullable RandomSpreadCanopy randomSpreadCanopy,
    /* Configuration for mangrove roots */
    @JsonProperty("mangrove_roots") @Nullable MangroveRoots mangroveRoots
) {
    
        /* Allows you to define a number of clusters for the base of the tree. Used to generate mega tree variants. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record BaseCluster(
            /* List of blocks that the base cluster of a tree can replace. */
            @JsonProperty("may_replace") List<String> mayReplace,
            /* Number of clusters that can be generated. */
            @JsonProperty("num_clusters") Integer numClusters,
            /* Radius where the clusters that can be generated. */
            @JsonProperty("cluster_radius") Integer clusterRadius
        ) {
        }
    
        /* Configutarion for the acacia trunk. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record AcaciaTrunk(
            @JsonProperty("trunk_width") @Nullable Integer trunkWidth,
            /* Configuration object for the trunk height. */
            @JsonProperty("trunk_height") @Nullable TrunkHeight trunkHeight,
            /* Configuration object for diagonal branches. */
            @JsonProperty("trunk_lean") @Nullable TrunkLean trunkLean,
            @JsonProperty("trunk_block") @Nullable String trunkBlock,
            /* Configuration object for branches. */
            @JsonProperty("branches") @Nullable Branches branches
        ) {
            
                /* Configuration object for the trunk height. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record TrunkHeight(
                    /* Min height for the trunk. */
                    @JsonProperty("base") @Nullable Integer base,
                    /* Intervals used to randomize the trunk height, the value of each interval will create a random number where (0 <= rand < interval)), and will be added to the height. */
                    @JsonProperty("intervals") @Nullable List<Integer> intervals,
                    /* Min height where the canopy can be placed. */
                    @JsonProperty("min_height_for_canopy") @Nullable Integer minHeightForCanopy
                ) {
                }
            
                /* Configuration object for diagonal branches. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record TrunkLean(
                    /* If true, diagonal branches will be created. */
                    @JsonProperty("allow_diagonal_growth") @Nullable Boolean allowDiagonalGrowth,
                    /* Number of blocks below the tree height at which diagonal branches can be created. */
                    @JsonProperty("lean_height") @Nullable Range_a_B leanHeight,
                    /* Number of steps taken in X/Z direction while creating a diagonal branch. */
                    @JsonProperty("lean_steps") @Nullable Range_a_B leanSteps,
                    /* Length for the diagonal branch in the Y axis. */
                    @JsonProperty("lean_length") @Nullable Range_a_B leanLength
                ) {
                }
            
                /* Configuration object for branches. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Branches(
                    /* Length for the branch in the Y axis. */
                    @JsonProperty("branch_length") @Nullable Range_a_B branchLength,
                    /* Starting Y position for the branch. */
                    @JsonProperty("branch_position") @Nullable Range_a_B branchPosition,
                    /* Probability of creating a branch. */
                    @JsonProperty("branch_chance") @Nullable ChanceInformation branchChance,
                    @JsonProperty("branch_canopy") @Nullable BranchCanopy branchCanopy,
                    @JsonProperty("trunk_decoration") @Nullable Decoration trunkDecoration
                ) {
                }
        }
    
        /* Configutarion for the cherry trunk. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record CherryTrunk(
            @JsonProperty("trunk_block") @Nullable String trunkBlock,
            /* Configuration object for the trunk height. */
            @JsonProperty("trunk_height") @Nullable TrunkHeight trunkHeight,
            /* Configuration object for branches. */
            @JsonProperty("branches") @Nullable Branches branches
        ) {
            
                /* Configuration object for the trunk height. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record TrunkHeight(
                    /* Min height for the trunk. */
                    @JsonProperty("base") @Nullable Integer base,
                    /* Intervals used to randomize the trunk height, the value of each interval will create a random number where (0 <= rand < interval)), and will be added to the height. */
                    @JsonProperty("intervals") @Nullable List<Integer> intervals
                ) {
                }
            
                /* Configuration object for branches. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Branches(
                    /* Configuration object to pick a tree variant based on a weighted random number */
                    @JsonProperty("tree_type_weights") @Nullable TreeTypeWeights treeTypeWeights,
                    /* Branch length in X/Z axis. */
                    @JsonProperty("branch_horizontal_length") @Nullable Range_a_B branchHorizontalLength,
                    /* Branch starting position relative to the top of the tree */
                    @JsonProperty("branch_start_offset_from_top") @Nullable Range_a_B branchStartOffsetFromTop,
                    /* Branch end position relative to the top of the tree */
                    @JsonProperty("branch_end_offset_from_top") @Nullable Range_a_B branchEndOffsetFromTop,
                    /* Probability of creating a branch. */
                    @JsonProperty("branch_chance") @Nullable ChanceInformation branchChance,
                    @JsonProperty("branch_canopy") @Nullable BranchCanopy branchCanopy
                ) {
                    
                        /* Configuration object to pick a tree variant based on a weighted random number */
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public record TreeTypeWeights(
                            /* Tree variant with one branch. */
                            @JsonProperty("one_branch") @Nullable Integer oneBranch,
                            /* Tree variant with two branches. */
                            @JsonProperty("two_branches") @Nullable Integer twoBranches,
                            /* Tree variant with three branch. */
                            @JsonProperty("two_branches_and_trunk") @Nullable Integer twoBranchesAndTrunk
                        ) {
                        }
                }
        }
    
        /* Configutarion for the fallen trunk. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record FallenTrunk(
            /* Length of the fallen log. */
            @JsonProperty("log_length") @Nullable Integer logLength,
            /* height of the stump. */
            @JsonProperty("stump_height") @Nullable Integer stumpHeight,
            /* Modifier for the length of the fallen log. */
            @JsonProperty("height_modifier") @Nullable Integer heightModifier,
            @JsonProperty("trunk_block") @Nullable String trunkBlock,
            /* Feature that can be used to decorate the fallen log. */
            @JsonProperty("log_decoration_feature") @Nullable String logDecorationFeature,
            @JsonProperty("trunk_decoration") @Nullable Decoration trunkDecoration
        ) {
        }
    
        /* Configutarion for the fancy trunk. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record FancyTrunk(
            /* Configuration object for the trunk height. */
            @JsonProperty("trunk_height") @Nullable TrunkHeight trunkHeight,
            @JsonProperty("trunk_width") @Nullable Integer trunkWidth,
            /* Configuration object for branches. */
            @JsonProperty("branches") @Nullable Branches branches,
            @JsonProperty("trunk_block") @Nullable String trunkBlock,
            /* Scale modifier for the tree radius. */
            @JsonProperty("width_scale") @Nullable Double widthScale,
            /* Min height for foliage. Represented by a percentage of the tree height. */
            @JsonProperty("foliage_altitude_factor") @Nullable Double foliageAltitudeFactor
        ) {
            
                /* Configuration object for the trunk height. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record TrunkHeight(
                    /* Min height for the trunk. */
                    @JsonProperty("base") @Nullable Integer base,
                    /* Modifier for the trunk height. */
                    @JsonProperty("variance") @Nullable Integer variance,
                    /* Final tree height is multiplied by this scale. */
                    @JsonProperty("scale") @Nullable Double scale
                ) {
                }
            
                /* Configuration object for branches. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Branches(
                    /* Slope for the branch, where 0 is horizontal and 1 is vertical. */
                    @JsonProperty("slope") @Nullable Double slope,
                    /* Density of foliage. */
                    @JsonProperty("density") @Nullable Double density,
                    /* Min height for branches. Represented by a percentage of the tree height. */
                    @JsonProperty("min_altitude_factor") @Nullable Double minAltitudeFactor
                ) {
                }
        }
    
        /* Configutarion for the mangrove trunk. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record MangroveTrunk(
            @JsonProperty("trunk_width") @Nullable Integer trunkWidth,
            /* Configuration object for the trunk height. */
            @JsonProperty("trunk_height") @Nullable TrunkHeight trunkHeight,
            @JsonProperty("trunk_block") @Nullable String trunkBlock,
            /* Configuration object for branches. */
            @JsonProperty("branches") @Nullable Branches branches,
            @JsonProperty("trunk_decoration") @Nullable Decoration trunkDecoration
        ) {
            
                /* Configuration object for the trunk height. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record TrunkHeight(
                    /* Min height for the trunk. */
                    @JsonProperty("base") @Nullable Integer base,
                    /* Tree height modifier A. */
                    @JsonProperty("height_rand_a") @Nullable Integer heightRandA,
                    /* Tree height modifier B. */
                    @JsonProperty("height_rand_b") @Nullable Integer heightRandB
                ) {
                }
            
                /* Configuration object for branches. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Branches(
                    /* Length for the branch in the Y axis. */
                    @JsonProperty("branch_length") @Nullable Range_a_B branchLength,
                    /* Number of branches to place. */
                    @JsonProperty("branch_steps") @Nullable Range_a_B branchSteps,
                    /* Probability of creating a branch. */
                    @JsonProperty("branch_chance") @Nullable ChanceInformation branchChance
                ) {
                }
        }
    
        /* Configutarion for the mega trunk. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record MegaTrunk(
            @JsonProperty("trunk_width") @Nullable Integer trunkWidth,
            /* Configuration object for the trunk height. */
            @JsonProperty("trunk_height") @Nullable TrunkHeight trunkHeight,
            @JsonProperty("trunk_block") @Nullable String trunkBlock,
            @JsonProperty("trunk_decoration") @Nullable Decoration trunkDecoration,
            /* Configuration object for branches. */
            @JsonProperty("branches") @Nullable Branches branches
        ) {
            
                /* Configuration object for the trunk height. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record TrunkHeight(
                    /* Min height for the trunk. */
                    @JsonProperty("base") @Nullable Integer base,
                    /* Intervals used to randomize the trunk height, the value of each interval will create a random number where (0 <= rand < interval)), and will be added to the height. */
                    @JsonProperty("intervals") @Nullable List<Integer> intervals,
                    /* Min height where the canopy can be placed. */
                    @JsonProperty("min_height_for_canopy") @Nullable Integer minHeightForCanopy
                ) {
                }
            
                /* Configuration object for branches. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record Branches(
                    /* Length for the branch. */
                    @JsonProperty("branch_length") @Nullable Integer branchLength,
                    /* Slope for the branch, where 0 is horizontal and 1 is vertical. */
                    @JsonProperty("branch_slope") @Nullable Double branchSlope,
                    /* Randomized distance between branches. */
                    @JsonProperty("branch_interval") @Nullable Range_a_B branchInterval,
                    /* Altitude at which branches can spawn, relative to the tree height. */
                    @JsonProperty("branch_altitude_factor") @Nullable BranchAltitudeFactor branchAltitudeFactor,
                    @JsonProperty("branch_canopy") @Nullable BranchCanopy branchCanopy
                ) {
                    
                        /* Altitude at which branches can spawn, relative to the tree height. */
                        @JsonIgnoreProperties(ignoreUnknown = true)
                        public record BranchAltitudeFactor(
                            /* Min altitude where branches can spawn. */
                            @JsonProperty("min") @Nullable Double min,
                            /* Max altitude where branches can spawn. */
                            @JsonProperty("max") @Nullable Double max
                        ) {
                        }
                }
        }
    
        /* Configutarion for the normal trunk. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Trunk(
            /* Defines the height of the trunk. */
            @JsonProperty("trunk_height") @Nullable Range_a_B trunkHeight,
            /* Modifier for the height of the trunk. */
            @JsonProperty("height_modifier") @Nullable Range_a_B heightModifier,
            /* Specifies if the trunk can be submerged. */
            @JsonProperty("can_be_submerged") @Nullable Object canBeSubmerged,
            @JsonProperty("trunk_block") @Nullable String trunkBlock,
            @JsonProperty("trunk_decoration") @Nullable Decoration trunkDecoration
        ) {
        }
    
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
    
        /* Configuration object for the random spread canopy. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record RandomSpreadCanopy(
            @JsonProperty("canopy_height") @Nullable Integer canopyHeight,
            @JsonProperty("canopy_radius") @Nullable Integer canopyRadius,
            /* Max number of attempts to create leaf blocks. */
            @JsonProperty("leaf_placement_attempts") @Nullable Integer leafPlacementAttempts,
            /* The blocks that form the canopy of the tree */
            @JsonProperty("leaf_blocks") @Nullable List<Object> leafBlocks
        ) {
        }
    
        /* Configuration for mangrove roots */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record MangroveRoots(
            /* Max width that the roots can occupy. The width increases up to the max width while moving downwards. When a max width is reached, roots will grow vertically */
            @JsonProperty("max_root_width") @Nullable Integer maxRootWidth,
            /* Max length that the roots can occupy. */
            @JsonProperty("max_root_length") @Nullable Integer maxRootLength,
            /* Block used for roots. */
            @JsonProperty("root_block") @Nullable String rootBlock,
            /* Configuration object for blocks decorating the top of the roots */
            @JsonProperty("above_root") @Nullable AboveRoot aboveRoot
        ) {
            
                /* Configuration object for blocks decorating the top of the roots */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record AboveRoot(
                    /* Probability of creating a block above the root */
                    @JsonProperty("above_root_chance") @Nullable ChanceInformation aboveRootChance,
                    /* The block placed on the top of the roots. */
                    @JsonProperty("above_root_block") @Nullable String aboveRootBlock,
                    /* The block used for muddy roots. */
                    @JsonProperty("muddy_root_block") @Nullable String muddyRootBlock,
                    /* The block used to determine if a muddy root should be placed. */
                    @JsonProperty("mud_block") @Nullable String mudBlock,
                    /* Root offset from the trunk */
                    @JsonProperty("y_offset") @Nullable Range_a_B yOffset,
                    /* List of blocks that a root can grow through. */
                    @JsonProperty("roots_may_grow_through") @Nullable List<String> rootsMayGrowThrough,
                    @JsonProperty("root_decoration") @Nullable Decoration rootDecoration
                ) {
                }
        }
}
