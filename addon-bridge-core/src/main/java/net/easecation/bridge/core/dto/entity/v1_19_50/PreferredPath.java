package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Specifies costing information for mobs that prefer to walk on preferred paths. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PreferredPath(
    /* Cost for non-preferred blocks. */
    @JsonProperty("default_block_cost") @Nullable Double defaultBlockCost,
    /* Added cost for jumping up a node. */
    @JsonProperty("jump_cost") @Nullable Integer jumpCost,
    /* Distance mob can fall without taking damage. */
    @JsonProperty("max_fall_blocks") @Nullable Integer maxFallBlocks,
    /* A list of blocks with their associated cost. */
    @JsonProperty("preferred_path_blocks") @Nullable List<Object> preferredPathBlocks
) {
}
