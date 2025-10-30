package net.easecation.bridge.core.dto.spawn_rule.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether the current temperature is a given type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsTarget(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The Biome temperature catagory to test. */
    @JsonProperty("value") String value
) {
}
