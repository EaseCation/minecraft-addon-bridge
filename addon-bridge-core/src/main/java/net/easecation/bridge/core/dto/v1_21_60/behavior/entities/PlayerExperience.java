package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines how much experience each player action should take. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerExperience(
    /* The initial value of the player experience. */
    @JsonProperty("value") @Nullable Integer value,
    /* The maximum player experience of this entity. */
    @JsonProperty("max") @Nullable Integer max
) {
}
