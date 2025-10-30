package net.easecation.bridge.core.dto.item.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Wearable item component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Wearable(
    /* Sets if the item can be dropped by a dispenser block */
    @JsonProperty("dispensable") @Nullable Boolean dispensable,
    /* Which equipment slot the item can fit in */
    @JsonProperty("slot") @Nullable String slot
) {
}
