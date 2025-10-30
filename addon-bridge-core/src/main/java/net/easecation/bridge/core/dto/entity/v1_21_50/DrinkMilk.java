package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to drink milk based on specified environment conditions. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DrinkMilk(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Time (in seconds) that the goal is on cooldown before it can be used again. */
    @JsonProperty("cooldown_seconds") @Nullable Double cooldownSeconds,
    /* Conditions that need to be met for the behavior to start. */
    @JsonProperty("filters") @Nullable Filters filters
) {
}
