package net.easecation.bridge.core.dto.feature_rule.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the designated equipment location for the subject entity is completely empty. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AllSlotsEmpty(
    /* Returns true when the designated equipment location for the subject entity is completely empty. */
    @JsonProperty("test") @Nullable String test,
    /* (Optional) The comparison to apply with {@code value}. */
    @JsonProperty("operator") @Nullable String operator,
    /* (Optional) The subject of this filter test. */
    @JsonProperty("subject") @Nullable String subject,
    /* The equipment location to test. */
    @JsonProperty("value") String value
) {
}
