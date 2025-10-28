package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject Player entity has opened a container. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasContainerOpen(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* (Optional) true or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
