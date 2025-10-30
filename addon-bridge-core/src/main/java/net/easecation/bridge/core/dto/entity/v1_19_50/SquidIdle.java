package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the squid to swim in place idly. Can only be used by the Squid. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SquidIdle(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
