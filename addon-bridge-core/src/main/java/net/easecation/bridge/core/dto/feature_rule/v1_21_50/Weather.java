package net.easecation.bridge.core.dto.feature_rule.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests for the current weather state the entity is experiencing. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Weather(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The Family name to look for. */
    @JsonProperty("value") String value
) {
}
