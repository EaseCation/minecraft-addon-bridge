package net.easecation.bridge.core.dto.feature_rule.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject Player entity has opened a container. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasContainerOpen(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* (Optional) true or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
