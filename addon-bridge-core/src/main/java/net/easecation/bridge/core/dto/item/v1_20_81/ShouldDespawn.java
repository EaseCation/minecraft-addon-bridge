package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* Should despawn component determines if the item should eventually despawn while floating in the world */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ShouldDespawn(
    /* Whether the item should eventually despawn while floating in the world */
    @JsonProperty("value") Boolean value
) {
}
