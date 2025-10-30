package net.easecation.bridge.core.dto.spawn_rule.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns the number of riders on this entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RiderCount(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* An integer value. */
    @JsonProperty("value") Integer value
) {
}
