package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the speed with which an entity can move through water. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UnderwaterMovement(
    /* Movement speed of the entity under water. */
    @JsonProperty("value") @Nullable Double value
) {
}
