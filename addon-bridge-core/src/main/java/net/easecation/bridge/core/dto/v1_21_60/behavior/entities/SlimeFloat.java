package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allow slimes to float in water / lava. Can only be used by Slime and Magma Cubes. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SlimeFloat(
    @JsonProperty("priority") @Nullable Priority priority,
    @JsonProperty("speed_multiplier") @Nullable SpeedMultiplier speedMultiplier,
    /* Percent chance a slime or magma cube has to jump while in water / lava. */
    @JsonProperty("jump_chance_percentage") @Nullable Double jumpChancePercentage
) {
}
