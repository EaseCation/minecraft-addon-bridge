package net.easecation.bridge.core.dto.feature_rule.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests is the mob is outside of the specified light level range (0, 16). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LightLevel(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* An integer value. */
    @JsonProperty("value") Integer value
) {
}
