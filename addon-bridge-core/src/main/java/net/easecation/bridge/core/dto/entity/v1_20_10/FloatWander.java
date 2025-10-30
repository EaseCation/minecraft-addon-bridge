package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to float around like the Ghast. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FloatWander(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Distance in blocks on ground that the mob will look for a new spot to move to. Must be at least 1 */
    @JsonProperty("xz_dist") @Nullable Integer xzDist,
    /* Distance in blocks that the mob will look up or down for a new spot to move to. Must be at least 1 */
    @JsonProperty("y_dist") @Nullable Integer yDist,
    /* Height in blocks to add to the selected target position. */
    @JsonProperty("y_offset") @Nullable Double yOffset,
    /* If true, the point has to be reachable to be a valid target. */
    @JsonProperty("must_reach") @Nullable Boolean mustReach,
    /* If true, the mob will randomly pick a new point while moving to the previously selected one. */
    @JsonProperty("random_reselect") @Nullable Boolean randomReselect,
    /* Range of time in seconds the mob will float around before landing and choosing to do something else. */
    @JsonProperty("float_duration") @Nullable Range_a_B floatDuration
) {
}
