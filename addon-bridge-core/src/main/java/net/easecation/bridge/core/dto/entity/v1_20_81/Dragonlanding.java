package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the Dragon to stop flying and transition into perching mode. Note: This behavior can only be used by the ender_dragon entity type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dragonlanding(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
