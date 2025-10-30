package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the dragon to look around for a player to attack while in perch mode. Can only be used by the Ender Dragon. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dragonscanning(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
