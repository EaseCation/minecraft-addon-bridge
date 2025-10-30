package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows mob to move towards its current target. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveTowardsTarget(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Defines the radius in blocks that the mob tries to be from the target. A value of 0 means it tries to occupy the same block as the target */
    @JsonProperty("within_radius") @Nullable Double withinRadius
) {
}
