package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the entity to be controlled by the player using an item in the item_controllable property (required). Also requires the minecraft:movement property, and the minecraft:rideable property. On every tick, the entity will attempt to rotate towards where the player is facing with the control item whilst simultaneously moving forward. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ControlledByPlayer(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The entity will attempt to rotate to face where the player is facing each tick. The entity will target this percentage of their difference in their current facing angles each tick (from 0.0 to 1.0 where 1.0 = 100%). This is limited by FractionalRotationLimit. A value of 0.0 will result in the entity no longer turning to where the player is facing. */
    @JsonProperty("fractional_rotation") @Nullable Double fractionalRotation,
    /* Limits the total degrees the entity can rotate to face where the player is facing on each tick. */
    @JsonProperty("fractional_rotation_limit") @Nullable Double fractionalRotationLimit,
    /* Speed multiplier of mount when controlled by player. */
    @JsonProperty("mount_speed_multiplier") @Nullable Double mountSpeedMultiplier
) {
}
