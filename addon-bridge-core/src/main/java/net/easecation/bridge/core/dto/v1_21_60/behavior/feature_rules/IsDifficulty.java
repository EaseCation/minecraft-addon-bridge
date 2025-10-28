package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the current difficulty level of the game. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsDifficulty(
    /* Tests the current difficulty level of the game. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The game's difficulty level to test. */
    @JsonProperty("value") String value
) {
}
