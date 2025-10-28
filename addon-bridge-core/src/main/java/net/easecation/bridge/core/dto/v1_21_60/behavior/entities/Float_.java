package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to stay afloat while swimming. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Float_(
    @JsonProperty("priority") @Nullable Integer priority,
    /* If true, the mob will keep sinking as long as it has passengers. */
    @JsonProperty("sink_with_passengers") @Nullable Boolean sinkWithPassengers
) {
}
