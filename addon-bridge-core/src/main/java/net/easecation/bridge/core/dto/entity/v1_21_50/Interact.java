package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines interactions with this entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Interact(
    /* The interactions. */
    @JsonProperty("interactions") @Nullable Object interactions
) {
}
