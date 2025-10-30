package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to run around aimlessly. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RunAroundLikeCrazy(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier
) {
}
