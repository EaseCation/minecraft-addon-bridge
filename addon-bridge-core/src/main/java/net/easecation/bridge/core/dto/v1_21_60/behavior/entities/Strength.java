package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the entity's strength to carry items. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Strength(
    /* The maximum strength of this entity. */
    @JsonProperty("max") @Nullable Integer max,
    /* The initial value of the strength. */
    @JsonProperty("value") @Nullable Integer value
) {
}
