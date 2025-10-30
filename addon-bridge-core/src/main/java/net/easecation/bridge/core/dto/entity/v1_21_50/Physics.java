package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the physical properties of an actor, including whether it is affected by gravity, whether it collides with objects, or whether it is pushed to the closest space. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Physics(
    /* Whether or not the entity collides with things. */
    @JsonProperty("has_collision") @Nullable Boolean hasCollision,
    /* Whether or not the entity is affected by gravity. */
    @JsonProperty("has_gravity") @Nullable Boolean hasGravity,
    /* Whether or not the entity is pushed to the closest space. */
    @JsonProperty("push_towards_closest_space") @Nullable Boolean pushTowardsClosestSpace
) {
}
