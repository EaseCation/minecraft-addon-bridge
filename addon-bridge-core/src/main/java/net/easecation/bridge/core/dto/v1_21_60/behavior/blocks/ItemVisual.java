package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;

/* The description identifier of the geometry and material used to render the item of this block. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemVisual(
    /* The "minecraft:geometry" component that will be used for the item. */
    @JsonProperty("geometry") Geometry geometry,
    /* The "minecraft:material_instances" component that will be used for the item. */
    @JsonProperty("material_instances") MaterialInstances materialInstances
) {
}
