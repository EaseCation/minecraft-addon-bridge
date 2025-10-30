package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Compares the current moon phase with an integer value in the range (0, 7). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoonPhase(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* An integer value. */
    @JsonProperty("value") Integer value
) {
}
