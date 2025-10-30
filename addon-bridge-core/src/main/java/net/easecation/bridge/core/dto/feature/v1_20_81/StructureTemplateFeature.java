package net.easecation.bridge.core.dto.feature.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/*
 * {@code minecraft:structure<i>template</i>feature} places a structure in the world. The structure must be stored as a .mcstructure file in the {@code structures} subdirectory of a behavior pack. It is possible to reference structures that are part of other behavior packs, they do not need to come from the same behavior pack as this feature. Constraints can be defined to specify where the structure is allowed to be placed. During placement, the feature will search for a position within the 'adjustment_radius' that satisfies all constraints. If none are found, the structure will not be placed.
 * Succeeds if: The structure is placed in the world.
 * Fails if: The structure fails to be placed within the world.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StructureTemplateFeature(
    @JsonProperty("description") Description description,
    /* Reference to the structure to be placed. */
    @JsonProperty("structure_name") String structureName,
    /* How far the structure is allowed to move when searching for a valid placement position. Search is radial, stopping when the nearest valid position is found. Defaults to 0 if omitted. */
    @JsonProperty("adjustment_radius") @Nullable Integer adjustmentRadius,
    /* Direction the structure will face when placed in the world. Defaults to {@code random} if omitted. */
    @JsonProperty("facing_direction") @Nullable String facingDirection,
    /* Specific constraints that must be satisfied when placing this structure. */
    @JsonProperty("constraints") Constraints constraints
) {
    
        /* Specific constraints that must be satisfied when placing this structure. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Constraints(
            /* When specified, ensures the structure is on the ground. */
            @JsonProperty("grounded") @Nullable net.easecation.bridge.core.dto.EmptyObject grounded,
            /* When specified, ensures the structure has air above it. */
            @JsonProperty("unburied") @Nullable net.easecation.bridge.core.dto.EmptyObject unburied,
            /* When specified, ensures the structure has air above it. */
            @JsonProperty("block_intersection") @Nullable BlockIntersection blockIntersection
        ) {
            
                /* When specified, ensures the structure has air above it. */
                @JsonIgnoreProperties(ignoreUnknown = true)
                public record BlockIntersection(
                    /* List of blocks the owning structure is allowed to intersect with. */
                    @JsonProperty("block_allowlist") @Nullable List<String> blockAllowlist,
                    /* List of blocks the owning structure is allowed to intersect with. */
                    @JsonProperty("block_whitelist") @Nullable List<String> blockWhitelist
                ) {
                }
        }
}
