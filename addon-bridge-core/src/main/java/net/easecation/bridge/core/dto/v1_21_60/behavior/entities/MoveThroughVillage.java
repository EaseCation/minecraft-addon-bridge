package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Can only be used by Villagers. Allows the villagers to create paths around the village. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveThroughVillage(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* If true, the mob will only move through the village during night time. */
    @JsonProperty("only_at_night") @Nullable Boolean onlyAtNight
) {
}
