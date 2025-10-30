package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;

/* Adds a trigger to call when this entity is set on fire. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnIgnite(
) {
}
