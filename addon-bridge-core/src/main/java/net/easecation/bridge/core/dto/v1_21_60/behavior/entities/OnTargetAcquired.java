package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Adds a trigger to call when this entity finds a target. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnTargetAcquired(
) {
}
