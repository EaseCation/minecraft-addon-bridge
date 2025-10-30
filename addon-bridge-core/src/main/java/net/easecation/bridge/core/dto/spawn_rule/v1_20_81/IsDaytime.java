package net.easecation.bridge.core.dto.spawn_rule.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true during the daylight hours. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsDaytime(
    /* Returns true during the daylight hours. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
