package net.easecation.bridge.core.dto.entity.v1_20_41;

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
