package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Describes the destructible by mining properties for this block. If set to true, the block will take the default number of seconds to destroy. If set to false, this block is indestructible by mining. If the component is omitted, the block will take the default number of seconds to destroy. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface DestructibleByMining {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record DestructibleByMining_Variant0(
        Boolean value
    ) implements DestructibleByMining {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record DestructibleByMining_Variant1(
        /* Sets the number of seconds it takes to destroy the block with base equipment. Greater numbers result in greater mining times. */
        @JsonProperty("seconds_to_destroy") @Nullable Double secondsToDestroy,
        /* Optional array of objects to describe item-specific block destroy speeds. */
        @JsonProperty("item_specific_speeds") @Nullable List<Object> itemSpecificSpeeds
    ) implements DestructibleByMining {}
}
