package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the Dragon to fly around in a circle around the center podium. Can only be used by the Ender Dragon. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dragonholdingpattern(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
