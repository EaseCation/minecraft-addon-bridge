package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this entity to fly around looking for a player to shoot fireballs at. Note: This behavior can only be used by the ender_dragon entity type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dragonstrafeplayer(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The speed this entity moves when this behavior has started or while it's active. */
    @JsonProperty("active_speed") @Nullable Double activeSpeed,
    /* Maximum distance of this entity's fireball attack while strafing. */
    @JsonProperty("fireball_range") @Nullable Double fireballRange,
    /* The speed this entity moves while this behavior is not active. */
    @JsonProperty("flight_speed") @Nullable Double flightSpeed,
    /* Percent chance to to switch this entity's strafe direction between clockwise and counterclockwise. Switch direction chance occurs each time a new target is chosen (1.0 = 100%). */
    @JsonProperty("switch_direction_probability") @Nullable Double switchDirectionProbability,
    /* Time (in seconds) the target must be in fireball range, and in view [ie, no solid terrain in-between the target and this entity], before a fireball can be shot. */
    @JsonProperty("target_in_range_and_in_view_time") @Nullable Double targetInRangeAndInViewTime,
    /* Minimum and maximum distance, from the target, this entity can use this behavior. */
    @JsonProperty("target_zone") @Nullable Range_a_B targetZone,
    /* The speed at which this entity turns while using this behavior. */
    @JsonProperty("turn_speed") @Nullable Double turnSpeed,
    /* The target must be within "view_angle" degrees of the dragon's current rotation before a fireball can be shot. */
    @JsonProperty("view_angle") @Nullable Double viewAngle
) {
}
