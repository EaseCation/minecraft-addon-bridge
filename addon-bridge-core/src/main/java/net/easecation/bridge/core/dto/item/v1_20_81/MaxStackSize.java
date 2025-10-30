package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* The max stack size component determines how many of the item can be stacked together. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MaxStackSize(
    /* How many of the item that can be stacked. */
    @JsonProperty("value") Double value
) {
}
