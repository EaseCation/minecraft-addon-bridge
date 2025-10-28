package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to stay put while it is in a sitting state instead of doing something else. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StayWhileSitting(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
