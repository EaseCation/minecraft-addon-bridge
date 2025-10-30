package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows monsters to jump at and attack their target. Can only be used by hostile mobs. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LeapAtTarget(
    @JsonProperty("priority") @Nullable Integer priority,
    /* If true, the mob will only jump at its target if its on the ground. Setting it to false will allow it to jump even if its already in the air */
    @JsonProperty("must_be_on_ground") @Nullable Boolean mustBeOnGround,
    /* Allows the actor to be set to persist upon targeting a player. */
    @JsonProperty("set_persistent") @Nullable Boolean setPersistent,
    /* The height in blocks the mob jumps when leaping at its target. */
    @JsonProperty("yd") @Nullable Double yd,
    /* Distance in blocks the mob jumps when leaping at its target. */
    @JsonProperty("target_dist") @Nullable Double targetDist
) {
}
