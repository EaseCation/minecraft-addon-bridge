package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines physics properties of an actor, including if it is affected by gravity or if it collides with objects. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Physics(
    /* Whether or not the object collides with things. */
    @JsonProperty("has_collision") @Nullable Boolean hasCollision,
    /* Whether or not the entity is affected by gravity. */
    @JsonProperty("has_gravity") @Nullable Boolean hasGravity
) {
}
