package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the speed with which an entity can move through water. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WaterMovement(
    /* Drag factor to determine movement speed when in water. */
    @JsonProperty("drag_factor") @Nullable Double dragFactor
) {
}
