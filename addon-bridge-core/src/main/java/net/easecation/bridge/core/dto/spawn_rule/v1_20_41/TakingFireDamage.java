package net.easecation.bridge.core.dto.spawn_rule.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests if the subject is taking fire damage. Requires the damage_sensor component */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TakingFireDamage(
    /* Tests if the subject is taking fire damage. Requires the damage_sensor component */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
