package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the current difficulty level of the game. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsDifficulty(
    /* Tests the current difficulty level of the game. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The game's difficulty level to test. */
    @JsonProperty("value") String value
) {
}
