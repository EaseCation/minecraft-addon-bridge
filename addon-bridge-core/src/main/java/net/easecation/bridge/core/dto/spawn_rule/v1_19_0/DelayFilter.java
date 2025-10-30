package net.easecation.bridge.core.dto.spawn_rule.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DelayFilter(
    /* UNDOCUMENTED. */
    @JsonProperty("min") @Nullable Integer min,
    /* UNDOCUMENTED. */
    @JsonProperty("max") @Nullable Integer max,
    /* UNDOCUMENTED. */
    @JsonProperty("identifier") @Nullable String identifier,
    /* UNDOCUMENTED. */
    @JsonProperty("spawn_chance") @Nullable Double spawnChance
) {
}
