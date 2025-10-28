package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Adds a trigger to call when this pet's owner awakes after sleeping with the pet. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnWakeWithOwner(
) {
}
