package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The components groups to add or remove. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AddOrRemove(
    /* The components groups to add or remove. */
    @JsonProperty("component_groups") @Nullable Object componentGroups
) {
}
