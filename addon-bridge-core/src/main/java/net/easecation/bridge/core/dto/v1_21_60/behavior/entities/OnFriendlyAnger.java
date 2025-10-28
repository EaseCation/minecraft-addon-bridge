package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Adds a trigger that will run when a nearby entity of the same type as this entity becomes Angry. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnFriendlyAnger(
) {
}
