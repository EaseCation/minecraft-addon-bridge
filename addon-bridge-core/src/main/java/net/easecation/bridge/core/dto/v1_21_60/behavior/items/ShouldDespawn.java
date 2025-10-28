package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;

/* Should despawn component determines if the item should eventually despawn while floating in the world */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface ShouldDespawn {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record ShouldDespawn_Variant0(
        Boolean value
    ) implements ShouldDespawn {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record ShouldDespawn_Variant1(
        /* Whether the item should eventually despawn while floating in the world */
        @JsonProperty("value") Boolean value
    ) implements ShouldDespawn {}
}
