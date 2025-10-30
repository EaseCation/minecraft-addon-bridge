package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows mobs with the dweller component to move toward their Village area that the mob should be restricted to. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveTowardsDwellingRestriction(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier
) {
}
