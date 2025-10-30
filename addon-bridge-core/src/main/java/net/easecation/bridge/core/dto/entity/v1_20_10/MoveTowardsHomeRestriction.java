package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows mobs with the home component to move toward their pre-defined area that the mob should be restricted to. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveTowardsHomeRestriction(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier
) {
}
