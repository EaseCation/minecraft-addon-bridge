package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Creates a trigger based on environment conditions. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EnvironmentSensor(
    /* The list of triggers that fire when the environment conditions match the given filter criteria. */
    @JsonProperty("triggers") @Nullable Object triggers
) {
}
