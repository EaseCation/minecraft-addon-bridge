package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* The can destroy in creative component determines if the item will break blocks in creative when swinging. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CanDestroyInCreative(
    /* Whether the item can destroy blocks while in creative */
    @JsonProperty("value") Boolean value
) {
}
