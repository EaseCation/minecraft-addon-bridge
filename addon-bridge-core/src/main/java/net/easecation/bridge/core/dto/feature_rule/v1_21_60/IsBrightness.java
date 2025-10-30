package net.easecation.bridge.core.dto.feature_rule.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the current brightness against a provided value in the range (0.0f, 1.0f). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsBrightness(
    /* Tests the current brightness against a provided value in the range (0.0f, 1.0f). */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The brightness value to compare with. */
    @JsonProperty("value") Double value
) {
}
