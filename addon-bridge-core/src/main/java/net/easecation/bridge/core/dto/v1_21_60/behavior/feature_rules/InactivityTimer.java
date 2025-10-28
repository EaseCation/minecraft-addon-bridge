package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests if the specified duration in seconds of inactivity for despawning has been reached. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InactivityTimer(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The Family name to look for. */
    @JsonProperty("value") Integer value
) {
}
