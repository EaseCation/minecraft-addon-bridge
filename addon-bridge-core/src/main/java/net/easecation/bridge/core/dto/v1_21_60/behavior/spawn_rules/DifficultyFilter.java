package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This component allows players to set mobs spawn at certain difficulty levels. The min is for Peaceful difficulty and the max is for Hard difficulty. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DifficultyFilter(
    /* This is the minimum difficulty level that a mob spawns. */
    @JsonProperty("min") @Nullable JjDifficulty min,
    /* This is the maximum difficulty level that a mob spawns. */
    @JsonProperty("max") @Nullable JjDifficulty max
) {
}
