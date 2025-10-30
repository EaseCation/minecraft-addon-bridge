package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;

/* Adds a trigger to call when this entity finds a target. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnTargetAcquired(
) {
}
