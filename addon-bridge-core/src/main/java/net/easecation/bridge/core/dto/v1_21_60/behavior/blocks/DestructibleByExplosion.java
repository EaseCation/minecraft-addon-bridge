package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Describes the destructible by explosion properties for this block. If set to true, the block will have the default explosion resistance. If set to false, this block is indestructible by explosion. If the component is omitted, the block will have the default explosion resistance. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface DestructibleByExplosion {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record DestructibleByExplosion_Variant0(
        Boolean value
    ) implements DestructibleByExplosion {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record DestructibleByExplosion_Variant1(
        /* Describes how resistant the block is to explosion. Greater values mean the block is less likely to break when near an explosion (or has higher resistance to explosions). The scale will be different for different explosion power levels. A negative value or 0 means it will easily explode; larger numbers increase level of resistance. */
        @JsonProperty("explosion_resistance") @Nullable Double explosionResistance
    ) implements DestructibleByExplosion {}
}
