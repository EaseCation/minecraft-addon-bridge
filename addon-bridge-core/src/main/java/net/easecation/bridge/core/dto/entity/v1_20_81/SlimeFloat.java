package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allow slimes to float in water / lava. Can only be used by Slime and Magma Cubes. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SlimeFloat(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Percent chance a slime or magma cube has to jump while in water / lava. */
    @JsonProperty("jump_chance_percentage") @Nullable Double jumpChancePercentage
) {
}
