package net.easecation.bridge.core.dto.spawn_rule.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DifficultyFilter(
    /* This is the minimum difficulty level that a mob spawns. */
    @JsonProperty("min") @Nullable String min,
    /* This is the maximum difficulty level that a mob spawns. */
    @JsonProperty("max") @Nullable String max
) {
}
