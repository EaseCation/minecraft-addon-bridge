package net.easecation.bridge.core.dto.feature_rule.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether the Subject is sleeping. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsSleeping(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
