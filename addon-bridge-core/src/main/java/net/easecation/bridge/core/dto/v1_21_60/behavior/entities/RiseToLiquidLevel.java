package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to stay at a certain level when in liquid. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RiseToLiquidLevel(
    @JsonProperty("priority") @Nullable Priority priority,
    /* Vertical offset from the liquid. */
    @JsonProperty("liquid_y_offset") @Nullable Double liquidYOffset,
    /* Displacement for how much the entity will move up in the vertical axis. */
    @JsonProperty("rise_delta") @Nullable Double riseDelta,
    /* Displacement for how much the entity will move down in the vertical axis. */
    @JsonProperty("sink_delta") @Nullable Double sinkDelta
) {
}
