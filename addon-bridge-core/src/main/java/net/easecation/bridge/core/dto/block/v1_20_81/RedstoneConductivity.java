package net.easecation.bridge.core.dto.block.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The basic redstone properties of a block; if the component is not provided the default values are used. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RedstoneConductivity(
    /* Specifies if redstone wire can stair-step downward on the block. */
    @JsonProperty("allows_wire_to_step_down") @Nullable Boolean allowsWireToStepDown,
    /* Specifies if the block can be powered by redstone. */
    @JsonProperty("redstone_conductor") @Nullable Boolean redstoneConductor
) {
}
