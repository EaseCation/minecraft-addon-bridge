package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Compares the distance to the nearest Player with a float value. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DistanceToNearestPlayer(
    /* Compares the distance to the nearest Player with a float value. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* (Required) A floating point value. */
    @JsonProperty("value") Double value
) {
}
