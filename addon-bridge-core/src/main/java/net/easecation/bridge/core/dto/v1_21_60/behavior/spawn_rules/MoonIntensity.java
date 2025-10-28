package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Compares the current moon intensity with a float value in the range (0.0, 1.0) */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoonIntensity(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* A floating point value. */
    @JsonProperty("value") Double value
) {
}
