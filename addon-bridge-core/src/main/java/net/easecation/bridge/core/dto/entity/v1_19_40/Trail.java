package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the entity's trail to carry items. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Trail(
    /* The type of block you wish to be spawned by the entity as it move about the world. Solid blocks may not be spawned at an offset of (). */
    @JsonProperty("block_type") @Nullable String blockType,
    /* One or more conditions that must be met in order to cause the chosen block type to spawn. */
    @JsonProperty("spawn_filter") @Nullable Filters spawnFilter,
    /* The distance from the entities current position to spawn the block. Capped at up to 16 blocks away. The X value is left/right(-/+), the Z value is backward/forward(-/+), the Y value is below/above(-/+). */
    @JsonProperty("spawn_offset") @Nullable List<Double> spawnOffset
) {
}
