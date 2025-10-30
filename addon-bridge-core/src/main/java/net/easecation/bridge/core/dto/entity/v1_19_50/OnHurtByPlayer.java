package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;

/* Adds a trigger to call when this entity is attacked by the player. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnHurtByPlayer(
) {
}
