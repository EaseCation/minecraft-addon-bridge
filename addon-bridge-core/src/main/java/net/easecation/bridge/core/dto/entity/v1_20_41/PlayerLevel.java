package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the player's level. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerLevel(
    /* The initial value of the player level. */
    @JsonProperty("value") @Nullable Integer value,
    /* The maximum player level value of the entity. */
    @JsonProperty("max") @Nullable Integer max
) {
}
