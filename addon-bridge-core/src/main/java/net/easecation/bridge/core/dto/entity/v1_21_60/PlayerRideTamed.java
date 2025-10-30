package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to be ridden by the player after being tamed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerRideTamed(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
