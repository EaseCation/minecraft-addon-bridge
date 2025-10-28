package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Compares the current moon phase with an integer value in the range (0, 7). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoonPhase(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* An integer value. */
    @JsonProperty("value") Integer value
) {
}
