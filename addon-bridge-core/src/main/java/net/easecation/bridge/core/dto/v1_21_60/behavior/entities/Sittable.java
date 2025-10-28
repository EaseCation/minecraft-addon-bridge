package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the entity's {@code sit} state. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Sittable(
    /* Event to run when the entity enters the {@code sit} state. */
    @JsonProperty("sit_event") @Nullable Event sitEvent,
    /* Event to run when the entity exits the {@code sit} state. */
    @JsonProperty("stand_event") @Nullable Event standEvent
) {
}
