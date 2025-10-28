package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the subject entity has the tag provided. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasTag(
    /* Returns true if the subject entity has the tag provided. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The tag as a string. */
    @JsonProperty("value") String value
) {
}
