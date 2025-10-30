package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Wearable item component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Wearable(
    /* How much protection does the armor item have. */
    @JsonProperty("protection") @Nullable Integer protection,
    /* Sets if the item can be dropped by a dispenser block */
    @JsonProperty("dispensable") @Nullable Boolean dispensable,
    /* Which equipment slot the item can fit in */
    @JsonProperty("slot") @Nullable String slot
) {
}
