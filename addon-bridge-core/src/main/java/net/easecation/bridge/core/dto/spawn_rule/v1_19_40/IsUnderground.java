package net.easecation.bridge.core.dto.spawn_rule.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject entity is underground. An entity is considered underground if there are non-solid blocks above it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsUnderground(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
