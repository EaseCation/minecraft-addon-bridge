package net.easecation.bridge.core.dto.feature_rule.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the current weather, at the actor's position, against a provided weather value. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherAtPosition(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The Family name to look for. */
    @JsonProperty("value") String value
) {
}
