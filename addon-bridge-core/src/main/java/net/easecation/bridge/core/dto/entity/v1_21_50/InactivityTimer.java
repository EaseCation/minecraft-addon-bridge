package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests if the specified duration in seconds of inactivity for despawning has been reached. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InactivityTimer(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The Family name to look for. */
    @JsonProperty("value") Integer value
) {
}
