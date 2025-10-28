package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This component allows players to set mobs spawn with certain time delays before they will spawn. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DelayFilter(
    /* This is the minimum delay that a mob spawns. */
    @JsonProperty("min") @Nullable Integer min,
    /* This is the maximum delay that a mob spawns. */
    @JsonProperty("max") @Nullable Integer max,
    /* The identifier of the mob that will spawn. */
    @JsonProperty("identifier") EntityIdentifier identifier,
    /* The percent chance that this entity will spawn. */
    @JsonProperty("spawn_chance") @Nullable Double spawnChance
) {
}
