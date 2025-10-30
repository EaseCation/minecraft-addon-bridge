package net.easecation.bridge.core.dto.feature_rule.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject entity is inside a specified Block type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InBlock(
    /* Returns true when the subject entity is inside a specified Block type. */
    @JsonProperty("test") @Nullable String test,
    /* (Optional) The comparison to apply with {@code value}. */
    @JsonProperty("operator") @Nullable String operator,
    /* (Optional) The subject of this filter test. */
    @JsonProperty("subject") @Nullable String subject,
    /* (Optional) A string value. */
    @JsonProperty("value") @Nullable String value
) {
}
