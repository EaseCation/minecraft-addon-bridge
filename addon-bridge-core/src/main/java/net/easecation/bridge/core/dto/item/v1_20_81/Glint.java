package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* The glint component determines whether the item has the enchanted glint render effect on it */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Glint(
    /* Whether the item has the glint effect. */
    @JsonProperty("value") Boolean value
) {
}
