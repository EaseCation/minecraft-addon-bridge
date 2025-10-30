package net.easecation.bridge.core.dto.spawn_rule.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject entity has the named ability. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasAbility(
    /* Returns true when the subject entity has the named ability. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* (Required) The Ability type to test. */
    @JsonProperty("value") String value
) {
}
