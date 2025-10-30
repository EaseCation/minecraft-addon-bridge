package net.easecation.bridge.core.dto.spawn_rule.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether the biome the subject is in has the specified tag. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasBiomeTag(
    /* Tests whether the biome the subject is in has the specified tag. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The tag to look for. */
    @JsonProperty("value") Object value
) {
}
