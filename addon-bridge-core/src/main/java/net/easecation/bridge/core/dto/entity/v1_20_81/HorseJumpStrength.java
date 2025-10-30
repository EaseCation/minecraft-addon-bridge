package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this mob to jump higher when being ridden by a player. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HorseJumpStrength(
    /* The multiplier to apply to the jumping height. */
    @JsonProperty("value") @Nullable Object value
) {
}
