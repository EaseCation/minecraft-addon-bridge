package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This allows the mob to roll forward. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Roll(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The probability that the mob will use the goal. */
    @JsonProperty("probability") @Nullable Double probability
) {
}
