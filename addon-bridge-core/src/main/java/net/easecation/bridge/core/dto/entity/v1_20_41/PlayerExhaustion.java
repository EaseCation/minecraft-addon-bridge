package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the player's exhaustion level. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerExhaustion(
    /* The initial value of the player exhaustion. */
    @JsonProperty("value") @Nullable Integer value,
    /* The maximum player exhaustion of this entity. */
    @JsonProperty("max") @Nullable Integer max
) {
}
