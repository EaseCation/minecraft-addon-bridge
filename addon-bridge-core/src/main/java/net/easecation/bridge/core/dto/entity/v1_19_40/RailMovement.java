package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the entity's movement on the rails. An entity with this component is only allowed to move on the rail. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RailMovement(
    /* Maximum speed that this entity will move at when on the rail. */
    @JsonProperty("max_speed") @Nullable Double maxSpeed
) {
}
