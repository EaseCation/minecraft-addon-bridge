package net.easecation.bridge.core.dto.spawn_rule.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/*
 * Compares the current time with a float value in the range (0.0, 1.0).
 * 0.0= Noon
 * 0.25= Sunset
 * 0.5= Midnight
 * 0.75= Sunrise
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ClockTime(
    /*
 * Compares the current time with a float value in the range (0.0, 1.0).
 * 0.0= Noon
 * 0.25= Sunset
 * 0.5= Midnight
 * 0.75= Sunrise
 */
    @JsonProperty("test") @Nullable String test,
    /* (Optional) The comparison to apply with {@code value}. */
    @JsonProperty("operator") @Nullable String operator,
    /* (Optional) The subject of this filter test. */
    @JsonProperty("subject") @Nullable String subject,
    /* (Required) A floating point value. */
    @JsonProperty("value") Double value
) {
}
