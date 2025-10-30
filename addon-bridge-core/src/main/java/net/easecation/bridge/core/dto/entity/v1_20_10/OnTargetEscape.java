package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;

/* Adds a trigger to call when this entity loses the target it currently has. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnTargetEscape(
) {
}
