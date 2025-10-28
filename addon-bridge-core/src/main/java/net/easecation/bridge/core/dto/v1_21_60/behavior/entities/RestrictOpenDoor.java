package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to stay indoors during night time. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RestrictOpenDoor(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
