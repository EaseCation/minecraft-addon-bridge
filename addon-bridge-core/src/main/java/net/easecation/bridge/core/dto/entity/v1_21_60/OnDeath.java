package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;

/* Adds a trigger to call on this entity's death. minecraft:on<i>death can only be used by the {@code ender</i>dragon} entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnDeath(
) {
}
