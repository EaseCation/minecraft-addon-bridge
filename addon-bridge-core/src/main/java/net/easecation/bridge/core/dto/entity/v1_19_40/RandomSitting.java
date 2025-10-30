package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to randomly sit for a duration. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomSitting(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Time in seconds the mob has to wait before using the goal again. */
    @JsonProperty("cooldown") @Nullable Double cooldown,
    /* Time in seconds the mob has to wait before using the goal again. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime,
    /* The minimum amount of time in seconds before the mob can stand back up. */
    @JsonProperty("min_sit_time") @Nullable Double minSitTime,
    /* This is the chance that the mob will start this goal, from 0 to 1. */
    @JsonProperty("start_chance") @Nullable Double startChance,
    /* This is the chance that the mob will stop this goal, from 0 to 1. */
    @JsonProperty("stop_chance") @Nullable Double stopChance
) {
}
