package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Creates a trigger based on environment conditions. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EnvironmentSensor(
    /* The list of triggers that fire when the environment conditions match the given filter criteria. */
    @JsonProperty("triggers") @Nullable Trigger triggers
) {
}
