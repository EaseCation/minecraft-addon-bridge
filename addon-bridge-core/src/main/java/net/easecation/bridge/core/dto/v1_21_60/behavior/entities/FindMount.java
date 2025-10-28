package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to look around for another mob to ride atop it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FindMount(
    @JsonProperty("priority") @Nullable Priority priority,
    /* If true, the mob will not go into water blocks when going towards a mount. */
    @JsonProperty("avoid_water") @Nullable Boolean avoidWater,
    /* This is the distance the mob needs to be, in blocks, from the desired mount to mount it. If the value is below 0, the mob will use its default attack distance */
    @JsonProperty("mount_distance") @Nullable Double mountDistance,
    /* Time the mob will wait before starting to move towards the mount. */
    @JsonProperty("start_delay") @Nullable Integer startDelay,
    /* If true, the mob will only look for a mount if it has a target. */
    @JsonProperty("target_needed") @Nullable Boolean targetNeeded,
    /* Distance in blocks within which the mob will look for a mount. */
    @JsonProperty("within_radius") @Nullable Double withinRadius,
    /* The number of failed attempts to make before this goal is no longer used. */
    @JsonProperty("max_failed_attempts") @Nullable Integer maxFailedAttempts
) {
}
