package net.easecation.bridge.core.dto.item.v1_20_10;

import com.fasterxml.jackson.annotation.*;

/* Dye powder, there are 16 kinds of dye. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DyePowder(
    /* Defines what color the dye is. */
    @JsonProperty("color") String color
) {
}
