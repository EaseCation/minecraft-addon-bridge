package net.easecation.bridge.core.dto.entity.v1_21_50;

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
