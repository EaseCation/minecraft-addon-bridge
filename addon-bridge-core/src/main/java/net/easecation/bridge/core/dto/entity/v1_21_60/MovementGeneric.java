package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This move control allows a mob to fly, swim, climb, etc. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MovementGeneric(
    /* The maximum number in degrees the mob can turn per tick. */
    @JsonProperty("max_turn") @Nullable Double maxTurn
) {
}
