package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests if the subject is taking fire damage. Requires the damage_sensor component */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TakingFireDamage(
    /* Tests if the subject is taking fire damage. Requires the damage_sensor component */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
