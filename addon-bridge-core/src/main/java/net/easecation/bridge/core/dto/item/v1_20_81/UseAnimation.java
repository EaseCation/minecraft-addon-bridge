package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* This component determines which animation plays when using the item. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UseAnimation(
    /* Which animation to play when using the item. */
    @JsonProperty("value") String value
) {
}
