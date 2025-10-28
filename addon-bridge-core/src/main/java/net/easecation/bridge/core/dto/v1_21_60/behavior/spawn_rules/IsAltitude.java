package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the current altitude against a provided value. 0= bedrock elevation. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsAltitude(
    /* Tests the current altitude against a provided value. 0= bedrock elevation. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The altitude value to compare with. */
    @JsonProperty("value") Integer value
) {
}
