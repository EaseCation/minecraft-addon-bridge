package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows mobs with the dweller component to move toward their Village area that the mob should be restricted to. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveTowardsDwellingRestriction(
    @JsonProperty("priority") @Nullable Priority priority,
    @JsonProperty("speed_multiplier") @Nullable SpeedMultiplier speedMultiplier
) {
}
