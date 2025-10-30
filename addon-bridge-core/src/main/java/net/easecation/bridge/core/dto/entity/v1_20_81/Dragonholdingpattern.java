package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the Dragon to fly around in a circle around the center podium. Note: This behavior can only be used by the ender_dragon entity type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dragonholdingpattern(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
