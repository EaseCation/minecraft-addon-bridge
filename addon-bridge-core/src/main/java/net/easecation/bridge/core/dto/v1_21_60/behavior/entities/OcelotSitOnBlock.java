package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows an entity to sit in place, similar to the ocelot entity animation pose. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OcelotSitOnBlock(
    @JsonProperty("priority") @Nullable Priority priority,
    @JsonProperty("speed_multiplier") @Nullable SpeedMultiplier speedMultiplier
) {
}
