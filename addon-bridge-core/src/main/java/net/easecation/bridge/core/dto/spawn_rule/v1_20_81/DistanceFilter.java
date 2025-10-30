package net.easecation.bridge.core.dto.spawn_rule.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This component allows players to set mobs spawn with certain distance levels. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DistanceFilter(
    /* This is the minimum distance level that a mob spawns. */
    @JsonProperty("min") @Nullable Integer min,
    /* This is the maximum distance level that a mob spawns. */
    @JsonProperty("max") @Nullable Integer max
) {
}
