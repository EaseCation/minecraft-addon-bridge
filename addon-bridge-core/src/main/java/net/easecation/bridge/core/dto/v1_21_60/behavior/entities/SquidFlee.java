package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the squid to swim away. Can only be used by the Squid. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SquidFlee(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
