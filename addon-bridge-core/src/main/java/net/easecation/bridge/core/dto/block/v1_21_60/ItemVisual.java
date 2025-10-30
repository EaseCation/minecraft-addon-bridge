package net.easecation.bridge.core.dto.block.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;

/*
 * The description identifier of the geometry and material used to render the item of this block.
 * Experimental toggles required: Upcoming Creator Features
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemVisual(
    /* The "minecraft:geometry" component that will be used for the item. */
    @JsonProperty("geometry") Geometry geometry,
    /* The "minecraft:material_instances" component that will be used for the item. */
    @JsonProperty("material_instances") Map<String, MaterialInstancesValue> materialInstances
) {
}
