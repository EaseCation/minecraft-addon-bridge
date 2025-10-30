package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Can only be used by Slimes and Magma Cubes. Allows the mob to use a melee attack like the slime's. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SlimeAttack(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Allows the actor to be set to persist upon targeting a player. */
    @JsonProperty("set_persistent") @Nullable Boolean setPersistent,
    /* Maximum rotation (in degrees), on the X-axis, this entity can rotate while trying to look at the target. */
    @JsonProperty("x_max_rotation") @Nullable Double xMaxRotation,
    /* Maximum rotation (in degrees), on the Y-axis, this entity can rotate while trying to look at the target. */
    @JsonProperty("y_max_rotation") @Nullable Double yMaxRotation
) {
}
