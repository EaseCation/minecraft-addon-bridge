package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to be tempted by food they like. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PetSleepWithOwner(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Distance in blocks within the mob considers it has reached the goal. This is the "wiggle room" to stop the AI from bouncing back and forth trying to reach a specific spot */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* Height in blocks from the owner the pet can be to sleep with owner. */
    @JsonProperty("search_height") @Nullable Integer searchHeight,
    /* The radius that the mob will search for an owner to curl up with. */
    @JsonProperty("search_radius") @Nullable Integer searchRadius,
    /* The range that the mob will search for an owner to curl up with. */
    @JsonProperty("search_range") @Nullable Integer searchRange
) {
}
