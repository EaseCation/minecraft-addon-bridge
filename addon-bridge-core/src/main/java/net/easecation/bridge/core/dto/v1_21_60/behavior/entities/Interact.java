package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines interactions with this entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Interact(
    /* The interactions. */
    @JsonProperty("interactions") @Nullable Object interactions
) {
}
