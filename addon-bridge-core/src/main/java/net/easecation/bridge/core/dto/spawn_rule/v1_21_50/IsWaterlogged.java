package net.easecation.bridge.core.dto.spawn_rule.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests if the subject block is submerged in water. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsWaterlogged(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* true or false. */
    @JsonProperty("value") Boolean value
) {
}
