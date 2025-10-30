package net.easecation.bridge.core.dto.feature_rule.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether a named game rule is active. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsGameRule(
    /* The Game Rule to test. */
    @JsonProperty("domain") Object domain,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* Tests whether a named game rule is active. */
    @JsonProperty("value") Boolean value
) {
}
