package net.easecation.bridge.core.dto.spawn_rule.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Compares the current 24 hour time with an int value in the range[0, 24000]. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HourlyClockTime(
    /* Compares the current 24 hour time with an int value in the range[0, 24000]. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* (Required) An integer value set between 0 and 24000. */
    @JsonProperty("value") Integer value
) {
}
