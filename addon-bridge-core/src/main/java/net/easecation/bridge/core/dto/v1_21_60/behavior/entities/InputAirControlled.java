package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/*
 * When configured as a rideable entity, the entity will be controlled using WASD controls and mouse to move in three dimensions.
 * Only available with {@code "use<i>beta</i>features": true} and likely to be drastically changed or removed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InputAirControlled(
    /* Modifies speed going backwards. */
    @JsonProperty("backwards_movement_modifier") @Nullable Double backwardsMovementModifier,
    /* Modifies the strafe speed. */
    @JsonProperty("strafe_speed_modifier") @Nullable Double strafeSpeedModifier
) {
}
