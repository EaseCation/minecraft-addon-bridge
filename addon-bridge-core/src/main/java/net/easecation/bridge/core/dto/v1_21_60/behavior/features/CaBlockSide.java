package net.easecation.bridge.core.dto.v1_21_60.behavior.features;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface CaBlockSide {
    /* Reference to the block it may attach to. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record CaBlockSide_Block(
        /* Reference to the block it may attach to. */
        BlockReference value
    ) implements CaBlockSide {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record CaBlockSide_Variant1(
    ) implements CaBlockSide {}
}
