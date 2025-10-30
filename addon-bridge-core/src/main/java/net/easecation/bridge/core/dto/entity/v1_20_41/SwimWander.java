package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Has the fish swim around when they can't pathfind. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SwimWander(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Percent chance to start wandering, when not path-finding. 1 = 100% */
    @JsonProperty("interval") @Nullable Double interval,
    /* Distance to look ahead for obstacle avoidance, while wandering. */
    @JsonProperty("look_ahead") @Nullable Double lookAhead,
    /* Amount of time (in seconds) to wander after wandering behavior was successfully started. */
    @JsonProperty("wander_time") @Nullable Double wanderTime
) {
}
