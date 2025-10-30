package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to follow other mobs. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FollowMob(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* The distance in blocks it will look for a mob to follow. */
    @JsonProperty("search_range") @Nullable Integer searchRange,
    /* The distance in blocks this mob stops from the mob it is following. */
    @JsonProperty("stop_distance") @Nullable Double stopDistance
) {
}
