package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* defines the movement of an entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MovementBasic(
    /* The maximum number in degrees the mob can turn per tick. */
    @JsonProperty("max_turn") @Nullable Double maxTurn
) {
}
