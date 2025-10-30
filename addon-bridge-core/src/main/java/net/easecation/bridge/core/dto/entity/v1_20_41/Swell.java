package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the creeper to swell up when a player is nearby. It can only be used by Creepers. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Swell(
    @JsonProperty("priority") @Nullable Integer priority,
    /* This mob starts swelling when a target is at least this many blocks away. */
    @JsonProperty("start_distance") @Nullable Double startDistance,
    /* This mob stops swelling when a target has moved away at least this many blocks. */
    @JsonProperty("stop_distance") @Nullable Double stopDistance
) {
}
