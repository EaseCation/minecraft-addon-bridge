package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A shortcut for creators to use Vanilla block states without needing to define and manage a series of events or triggers on custom blocks */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Traits(
    @JsonProperty("minecraft:placement_direction") @Nullable PlacementDirection minecraft_placementDirection,
    @JsonProperty("minecraft:placement_position") @Nullable PlacementPosition minecraft_placementPosition
) {
}
