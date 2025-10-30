package net.easecation.bridge.core.dto.spawn_rule.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether the Subject is currently in the named biome. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsBiome(
    /* Tests whether the Subject is currently in the named biome. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The Biome type to test. */
    @JsonProperty("value") String value
) {
}
