package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Can only be used by Slimes and Magma Cubes. Allows the mob to continuously jump around like a slime. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SlimeKeepOnJumping(
    @JsonProperty("priority") @Nullable Priority priority,
    @JsonProperty("speed_multiplier") @Nullable SpeedMultiplier speedMultiplier
) {
}
