package net.easecation.bridge.core.dto.feature_rule.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the current altitude against a provided value. 0= bedrock elevation. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsAltitude(
    /* Tests the current altitude against a provided value. 0= bedrock elevation. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The altitude value to compare with. */
    @JsonProperty("value") Integer value
) {
}
