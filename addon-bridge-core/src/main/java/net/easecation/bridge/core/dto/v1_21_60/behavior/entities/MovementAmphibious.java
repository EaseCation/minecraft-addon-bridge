package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This move control allows the mob to swim in water and walk on land. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MovementAmphibious(
    /* The maximum number in degrees the mob can turn per tick. */
    @JsonProperty("max_turn") @Nullable Double maxTurn
) {
}
