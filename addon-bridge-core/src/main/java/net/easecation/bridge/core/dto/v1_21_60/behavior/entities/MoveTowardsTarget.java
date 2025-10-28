package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows mob to move towards its current target. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveTowardsTarget(
    @JsonProperty("priority") @Nullable Priority priority,
    @JsonProperty("speed_multiplier") @Nullable SpeedMultiplier speedMultiplier,
    /* Defines the radius in blocks that the mob tries to be from the target. A value of 0 means it tries to occupy the same block as the target */
    @JsonProperty("within_radius") @Nullable Double withinRadius
) {
}
