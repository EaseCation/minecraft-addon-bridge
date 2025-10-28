package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests is the mob is outside of the specified light level range (0, 16). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LightLevel(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* An integer value. */
    @JsonProperty("value") Integer value
) {
}
