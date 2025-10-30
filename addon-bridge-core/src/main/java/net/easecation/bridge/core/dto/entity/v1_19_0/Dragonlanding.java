package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the Dragon to stop flying and transition into perching mode. Can only be used by the Ender Dragon. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dragonlanding(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
