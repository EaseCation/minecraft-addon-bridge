package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This move control causes the mob to hover. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MovementHover(
    /* The maximum number in degrees the mob can turn per tick. */
    @JsonProperty("max_turn") @Nullable Double maxTurn
) {
}
