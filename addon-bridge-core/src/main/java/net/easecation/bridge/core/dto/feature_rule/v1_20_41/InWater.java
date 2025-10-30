package net.easecation.bridge.core.dto.feature_rule.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject entity is in water. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InWater(
    /* Returns true when the subject entity is in water. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
