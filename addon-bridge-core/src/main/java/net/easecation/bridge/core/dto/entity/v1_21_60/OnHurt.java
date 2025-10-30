package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;

/* Adds a trigger to call when this entity takes damage. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnHurt(
) {
}
