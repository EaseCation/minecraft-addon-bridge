package net.easecation.bridge.core.dto.feature_rule.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether the Subject has the specified mob effect. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasMobEffect(
    /* Tests whether the Subject has the specified mob effect. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The specified mob effect. */
    @JsonProperty("value") String value
) {
}
