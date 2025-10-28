package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether a named game rule is active. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsGameRule(
    /* The Game Rule to test. */
    @JsonProperty("domain") Object domain,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* Tests whether a named game rule is active. */
    @JsonProperty("value") Boolean value
) {
}
