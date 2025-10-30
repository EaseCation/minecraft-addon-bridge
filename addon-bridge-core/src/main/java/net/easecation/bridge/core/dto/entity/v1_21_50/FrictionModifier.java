package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines how much does friction affect this entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FrictionModifier(
    /* The higher the number, the more the friction affects this entity. A value of 1.0 means regular friction, while 2.0 means twice as much */
    @JsonProperty("value") @Nullable Double value
) {
}
