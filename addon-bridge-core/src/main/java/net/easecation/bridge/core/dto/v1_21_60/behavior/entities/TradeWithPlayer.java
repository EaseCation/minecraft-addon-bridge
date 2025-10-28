package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the player to trade with this mob. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TradeWithPlayer(
    @JsonProperty("priority") @Nullable Priority priority,
    /* Conditions that need to be met for the behavior to start. */
    @JsonProperty("filters") @Nullable Filters filters
) {
}
