package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Gives the entity the ability to jump. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record JumpStatic(
    /* The initial vertical velocity for the jump. */
    @JsonProperty("jump_power") @Nullable Double jumpPower
) {
}
