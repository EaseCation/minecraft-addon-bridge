package net.easecation.bridge.core.dto.spawn_rule.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This component allows players to set mobs spawn after a certain amount of time has passed within a world. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WorldAgeFilter(
    /* This is the minimum world<i>age</i>filter level that a mob spawns. */
    @JsonProperty("min") @Nullable Integer min
) {
}
