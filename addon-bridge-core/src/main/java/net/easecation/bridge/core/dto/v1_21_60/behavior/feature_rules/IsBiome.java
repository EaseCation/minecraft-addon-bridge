package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether the Subject is currently in the named biome. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsBiome(
    /* Tests whether the Subject is currently in the named biome. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The biome type to test. */
    @JsonProperty("value") String value
) {
}
