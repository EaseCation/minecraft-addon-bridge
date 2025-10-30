package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Applies defined amount of damage to the entity at specified intervals. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DamageOverTime(
    /* Amount of damage caused each hurt. */
    @JsonProperty("damage_per_hurt") @Nullable Integer damagePerHurt,
    /* Time in seconds between damage. */
    @JsonProperty("time_between_hurt") @Nullable Double timeBetweenHurt
) {
}
