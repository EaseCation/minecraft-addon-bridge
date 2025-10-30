package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* This component determines if an item is rendered like a tool while in hand. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HandEquipped(
    /* If the item is rendered like a tool while in hand. */
    @JsonProperty("value") Boolean value
) {
}
