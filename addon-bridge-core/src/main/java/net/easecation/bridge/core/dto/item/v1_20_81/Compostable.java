package net.easecation.bridge.core.dto.item.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows items to be compostable in the composter block. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Compostable(
    /* The chance of this item generating a compost layer when supplied to the composter block. */
    @JsonProperty("composting_chance") @Nullable Integer compostingChance
) {
}
