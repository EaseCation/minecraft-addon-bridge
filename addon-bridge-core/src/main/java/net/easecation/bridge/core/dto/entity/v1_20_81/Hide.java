package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows a mob with the hide component to attempt to move to - and hide at - an owned or nearby POI. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Hide(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Amount of time in seconds that the mob reacts. */
    @JsonProperty("duration") @Nullable Double duration,
    /* Defines what POI type to hide at. */
    @JsonProperty("poi_type") @Nullable String poiType,
    /* The cooldown time in seconds before the goal can be reused after a internal failure or timeout condition. */
    @JsonProperty("timeout_cooldown") @Nullable Double timeoutCooldown
) {
}
