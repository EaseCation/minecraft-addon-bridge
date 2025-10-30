package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this mob to break doors. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BreakDoor(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier
) {
}
