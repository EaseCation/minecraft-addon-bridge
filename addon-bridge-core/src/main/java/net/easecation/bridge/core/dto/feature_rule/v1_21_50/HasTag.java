package net.easecation.bridge.core.dto.feature_rule.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the subject entity has the tag provided. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasTag(
    /* Returns true if the subject entity has the tag provided. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The tag as a string. */
    @JsonProperty("value") String value
) {
}
