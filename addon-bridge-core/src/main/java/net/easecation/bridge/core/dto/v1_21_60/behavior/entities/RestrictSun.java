package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to automatically start avoiding the sun when its a clear day out. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RestrictSun(
    @JsonProperty("priority") @Nullable Priority priority
) {
}
