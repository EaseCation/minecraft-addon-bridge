package net.easecation.bridge.core.dto.feature.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Configuration object for the decoration. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Decoration(
    /* Probability of decorating the trunk. */
    @JsonProperty("decoration_chance") @Nullable ChanceInformation decorationChance,
    /* The block used for decorating the trunk. */
    @JsonProperty("decoration_block") @Nullable String decorationBlock,
    /* Number of decoration blocks to place. */
    @JsonProperty("num_steps") @Nullable Integer numSteps,
    /* Directions to spread decoration blocks. */
    @JsonProperty("step_direction") @Nullable String stepDirection
) {
}
