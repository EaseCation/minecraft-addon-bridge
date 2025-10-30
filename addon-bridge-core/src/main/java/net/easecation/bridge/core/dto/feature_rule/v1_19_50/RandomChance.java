package net.easecation.bridge.core.dto.feature_rule.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the random chance rolls 0 out of a specified Maximum range. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomChance(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* An integer value. */
    @JsonProperty("value") Integer value
) {
}
