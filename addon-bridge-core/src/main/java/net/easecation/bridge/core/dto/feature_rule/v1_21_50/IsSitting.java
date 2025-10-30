package net.easecation.bridge.core.dto.feature_rule.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the subject entity is sitting */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsSitting(
    /* Returns true if the subject entity is sitting. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
