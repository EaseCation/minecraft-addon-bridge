package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Specifies the initial value of a specific attribute for an entity when spawned. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Attribute(
    /* The minimum starting health an entity has. */
    @JsonProperty("min") @Nullable Double min,
    /* The maximum starting health an entity has. */
    @JsonProperty("max") @Nullable Double max,
    /* The amount of health an entity to start with by default. */
    @JsonProperty("value") @Nullable Range_a_B_ value
) {
}
