package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests for the current weather state the entity is experiencing. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Weather(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The Family name to look for. */
    @JsonProperty("value") String value
) {
}
