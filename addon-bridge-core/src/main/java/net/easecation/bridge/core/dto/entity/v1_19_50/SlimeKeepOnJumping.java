package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Can only be used by Slimes and Magma Cubes. Allows the mob to continuously jump around like a slime. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SlimeKeepOnJumping(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier
) {
}
