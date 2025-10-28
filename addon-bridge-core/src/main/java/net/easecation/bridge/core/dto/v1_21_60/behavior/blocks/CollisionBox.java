package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* This component can be specified as a Boolean. If this component is omitted, the default value for this component is true, which will give your block the default values for its parameters (a collision box the size/shape of a regular block). */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface CollisionBox {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record CollisionBox_Variant0(
        Boolean value
    ) implements CollisionBox {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record CollisionBox_Variant1(
        /* Minimal position of the bounds of the collision box. origin is specified as [x, y, z] and must be in the range (-8, 0, -8) to (8, 16, 8), inclusive. */
        @JsonProperty("origin") @Nullable List<Double> origin,
        /* Size of each side of the collision box. Size is specified as [x, y, z]. origin + size must be in the range (-8, 0, -8) to (8, 16, 8), inclusive. */
        @JsonProperty("size") @Nullable List<Double> size
    ) implements CollisionBox {}
}
