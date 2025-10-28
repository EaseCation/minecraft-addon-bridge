package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject entity has the named ability. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasAbility(
    /* Returns true when the subject entity has the named ability. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* (Required) The Ability type to test. */
    @JsonProperty("value") String value
) {
}
