package net.easecation.bridge.core.dto.spawn_rule.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the health of the subject. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ActorHealth(
    /* Tests the health of the subject. */
    @JsonProperty("test") @Nullable String test,
    /* (Optional) The comparison to apply with {@code value}. */
    @JsonProperty("operator") @Nullable String operator,
    /* (Optional) The subject of this filter test. */
    @JsonProperty("subject") @Nullable String subject,
    /* (Required) A integer value. */
    @JsonProperty("value") Integer value
) {
}
