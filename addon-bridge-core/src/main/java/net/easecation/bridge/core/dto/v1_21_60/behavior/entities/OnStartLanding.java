package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Only usable by the Ender Dragon. Adds a trigger to call when this entity lands. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnStartLanding(
) {
}
