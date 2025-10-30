package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;

/* Only usable by the Ender Dragon. Adds a trigger to call when this entity starts flying. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnStartTakeoff(
) {
}
