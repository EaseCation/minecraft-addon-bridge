package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Can only be used by Villagers. Allows them to seek shelter indoors. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveIndoors(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* The cooldown time in seconds before the goal can be reused after pathfinding fails. */
    @JsonProperty("timeout_cooldown") @Nullable Double timeoutCooldown
) {
}
