package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines a dynamic type jump control that will change jump properties based on the speed modifier of the mob. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Physics(
    /* Whether or not the object collides with things. */
    @JsonProperty("has_collision") @Nullable Boolean hasCollision,
    /* Whether or not the entity is affected by gravity. */
    @JsonProperty("has_gravity") @Nullable Boolean hasGravity
) {
}
