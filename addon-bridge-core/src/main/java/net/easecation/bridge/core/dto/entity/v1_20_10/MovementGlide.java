package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This is the move control for a flying mob that has a gliding movement. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MovementGlide(
    /* The maximum number in degrees the mob can turn per tick. */
    @JsonProperty("max_turn") @Nullable Double maxTurn,
    /* UNDOCUMENTED. */
    @JsonProperty("start_speed") @Nullable Double startSpeed,
    /* UNDOCUMENTED. */
    @JsonProperty("speed_when_turning") @Nullable Double speedWhenTurning
) {
}
