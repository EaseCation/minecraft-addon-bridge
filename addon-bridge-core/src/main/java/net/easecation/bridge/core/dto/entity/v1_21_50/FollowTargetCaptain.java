package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows mob to move towards its current target captain. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FollowTargetCaptain(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Defines the distance in blocks the mob will stay from its target while following. */
    @JsonProperty("follow_distance") @Nullable Double followDistance,
    /* Defines the maximum distance in blocks a mob can get from its target captain before giving up trying to follow it. */
    @JsonProperty("within_radius") @Nullable Double withinRadius
) {
}
