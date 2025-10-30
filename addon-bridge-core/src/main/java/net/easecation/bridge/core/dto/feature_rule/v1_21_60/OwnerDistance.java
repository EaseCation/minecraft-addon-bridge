package net.easecation.bridge.core.dto.feature_rule.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the distance between the subject and its owner. Returns false if there is no owner. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OwnerDistance(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* (Required) A floating point value. */
    @JsonProperty("value") Double value
) {
}
