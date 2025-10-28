package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests if the subject's persistence matches the bool value passed in. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsPersistent(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
