package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to seek shade. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FindCover(
    @JsonProperty("priority") @Nullable Priority priority,
    @JsonProperty("speed_multiplier") @Nullable SpeedMultiplier speedMultiplier,
    /* Time in seconds the mob has to wait before using the goal again. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime
) {
}
