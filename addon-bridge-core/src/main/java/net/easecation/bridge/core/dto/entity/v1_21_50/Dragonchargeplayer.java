package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this entity to attack a player by charging at them. The player is chosen by the "minecraft:behavior.dragonscanning". Note: This behavior can only be used by the ender_dragon entity type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dragonchargeplayer(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The speed this entity moves when this behavior has started or while it's active. */
    @JsonProperty("active_speed") @Nullable Double activeSpeed,
    /* If the dragon is outside the "target<i>zone" for longer than "continue</i>charge<i>threshold</i>time" seconds, the charge is canceled. */
    @JsonProperty("continue_charge_threshold_time") @Nullable Double continueChargeThresholdTime,
    /* The speed this entity moves while this behavior is not active. */
    @JsonProperty("flight_speed") @Nullable Double flightSpeed,
    /* Minimum and maximum distance, from the target, this entity can use this behavior. */
    @JsonProperty("target_zone") @Nullable Range_a_B targetZone,
    /* The speed at which this entity turns while using this behavior. */
    @JsonProperty("turn_speed") @Nullable Double turnSpeed
) {
}
