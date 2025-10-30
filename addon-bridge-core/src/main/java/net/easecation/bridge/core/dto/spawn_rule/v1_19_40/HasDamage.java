package net.easecation.bridge.core.dto.spawn_rule.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject entity receives the named damage type. has_damage can also use subject and operator parameters but they are optional. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasDamage(
    /* Returns true when the subject entity receives the named damage type. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The Damage type to test. */
    @JsonProperty("value") String value
) {
}
