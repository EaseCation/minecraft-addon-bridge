package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* The liquid clipped component determines whether the item interacts with liquid blocks on use. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LiquidClipped(
    /* Whether the item interacts with liquid blocks on use. */
    @JsonProperty("value") Boolean value
) {
}
