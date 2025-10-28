package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the subject entity has a valid target. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasTarget(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
