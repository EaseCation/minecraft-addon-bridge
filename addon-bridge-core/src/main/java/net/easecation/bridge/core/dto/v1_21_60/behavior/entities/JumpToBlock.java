package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows an entity to jump to another random block. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record JumpToBlock(
    @JsonProperty("priority") @Nullable Priority priority,
    /* Minimum and maximum cooldown time-range (positive, in seconds) between each attempted jump. */
    @JsonProperty("cooldown_range") @Nullable VectorOf2Items cooldownRange,
    /* Blocks that the mob can't jump to. */
    @JsonProperty("forbidden_blocks") @Nullable List<EntitiesBb> forbiddenBlocks,
    /* The maximum velocity with which the mob can jump. */
    @JsonProperty("max_velocity") @Nullable Double maxVelocity,
    /* The minimum distance (in blocks) from the mob to a block, in order to consider jumping to it. */
    @JsonProperty("minimum_distance") @Nullable Integer minimumDistance,
    /* The minimum length (in blocks) of the mobs path to a block, in order to consider jumping to it. */
    @JsonProperty("minimum_path_length") @Nullable Integer minimumPathLength,
    /* Blocks that the mob prefers jumping to. */
    @JsonProperty("preferred_blocks") @Nullable List<EntitiesBb> preferredBlocks,
    /* Chance (between 0.0 and 1.0) that the mob will jump to a preferred block, if in range. Only matters if preferred blocks are defined. */
    @JsonProperty("preferred_blocks_chance") @Nullable Double preferredBlocksChance,
    /* The scalefactor of the bounding box of the mob while it is jumping. */
    @JsonProperty("scale_factor") @Nullable Double scaleFactor,
    /* The height (in blocks, in range [2, 15]) of the search box, centered around the mob. */
    @JsonProperty("search_height") @Nullable Integer searchHeight,
    /* The width (in blocks, in range [2, 15]) of the search box, centered around the mob. */
    @JsonProperty("search_width") @Nullable Integer searchWidth
) {
}
