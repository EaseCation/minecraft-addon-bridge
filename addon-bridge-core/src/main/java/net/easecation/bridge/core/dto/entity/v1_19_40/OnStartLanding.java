package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;

/* Only usable by the Ender Dragon. Adds a trigger to call when this entity lands. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnStartLanding(
) {
}
