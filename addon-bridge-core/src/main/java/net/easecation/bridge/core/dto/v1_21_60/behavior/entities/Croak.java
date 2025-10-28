package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* [EXPERIMENTAL BEHAVIOR] Allows the entity to croak at a random time interval with configurable conditions. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Croak(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Random range in seconds after which the croaking stops. Can also be a constant. */
    @JsonProperty("duration") @Nullable Dcb duration,
    /* Conditions for the behavior to start and keep running. The interval between runs only starts after passing the filters. */
    @JsonProperty("filters") @Nullable Filters filters,
    /* Random range in seconds between runs of this behavior. Can also be a constant. */
    @JsonProperty("interval") @Nullable Dcc interval
) {
}
