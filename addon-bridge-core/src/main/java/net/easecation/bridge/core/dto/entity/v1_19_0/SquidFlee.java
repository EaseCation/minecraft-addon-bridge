package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the squid to swim away. Can only be used by the Squid. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SquidFlee(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
