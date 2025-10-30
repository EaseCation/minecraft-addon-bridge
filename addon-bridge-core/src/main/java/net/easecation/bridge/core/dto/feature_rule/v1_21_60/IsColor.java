package net.easecation.bridge.core.dto.feature_rule.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the subject entity is the named color. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsColor(
    /* Returns true if the subject entity is the named color. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The Palette Color to test. */
    @JsonProperty("value") String value
) {
}
