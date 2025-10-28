package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the squid to move away from ground blocks and back to water. Can only be used by the Squid. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SquidMoveAwayFromGround(
    @JsonProperty("priority") @Nullable Priority priority
) {
}
