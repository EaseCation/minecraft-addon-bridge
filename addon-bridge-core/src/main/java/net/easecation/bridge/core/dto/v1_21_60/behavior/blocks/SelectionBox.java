package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the area of the block that is selected by the player's cursor. If set to true, default values are used. If set to false, this block is not selectable by the player's cursor. If this component is omitted, default values are used. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface SelectionBox {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record SelectionBox_Variant0(
        Boolean value
    ) implements SelectionBox {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record SelectionBox_Variant1(
        /* Minimal position of the bounds of the selection box. "origin" is specified as [x, y, z] and must be in the range (-8, 0, -8) to (8, 16, 8), inclusive. */
        @JsonProperty("origin") @Nullable List<Double> origin,
        /* Size of each side of the selection box. Size is specified as [x, y, z]. "origin" + "size" must be in the range (-8, 0, -8) to (8, 16, 8), inclusive. */
        @JsonProperty("size") @Nullable List<Double> size
    ) implements SelectionBox {}
}
