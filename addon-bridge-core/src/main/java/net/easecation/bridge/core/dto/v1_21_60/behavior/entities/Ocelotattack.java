package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Can only be used by the Ocelot. Allows it to perform the sneak and pounce attack. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Ocelotattack(
    @JsonProperty("priority") @Nullable Priority priority,
    /* Time (in seconds) between attacks. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime,
    /* Max distance from the target, this entity will use this attack behavior. */
    @JsonProperty("max_distance") @Nullable Double maxDistance,
    /* Max distance from the target, this entity starts sneaking. */
    @JsonProperty("max_sneak_range") @Nullable Double maxSneakRange,
    /* Max distance from the target, this entity starts sprinting (sprinting takes priority over sneaking). */
    @JsonProperty("max_sprint_range") @Nullable Double maxSprintRange,
    /* Used with the base size of the entity to determine minimum target-distance before trying to deal attack damage. */
    @JsonProperty("reach_multiplier") @Nullable Double reachMultiplier,
    /* Modifies the attacking entity's movement speed while sneaking. */
    @JsonProperty("sneak_speed_multiplier") @Nullable Double sneakSpeedMultiplier,
    /* Modifies the attacking entity's movement speed while sprinting. */
    @JsonProperty("sprint_speed_multiplier") @Nullable Double sprintSpeedMultiplier,
    /* Modifies the attacking entity's movement speed when not sneaking or sprinting, but still within attack range. */
    @JsonProperty("walk_speed_multiplier") @Nullable Double walkSpeedMultiplier,
    /* Maximum rotation (in degrees), on the X-axis, this entity can rotate while trying to look at the target. */
    @JsonProperty("x_max_rotation") @Nullable Double xMaxRotation,
    /* Maximum rotation (in degrees), on the Y-axis, this entity can rotate its head while trying to look at the target. */
    @JsonProperty("y_max_head_rotation") @Nullable Double yMaxHeadRotation
) {
}
