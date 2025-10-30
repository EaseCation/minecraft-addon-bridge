package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;

/* The allow off hand component determines whether the item can be placed in the off hand slot of the inventory. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AllowOffHand(
    /* Whether the item can be placed in the off hand slot */
    @JsonProperty("value") Boolean value
) {
}
