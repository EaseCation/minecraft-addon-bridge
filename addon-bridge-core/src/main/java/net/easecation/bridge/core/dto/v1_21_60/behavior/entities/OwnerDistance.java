package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the distance between the subject and its owner. Returns false if there is no owner. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OwnerDistance(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* (Required) A floating point value. */
    @JsonProperty("value") Double value
) {
}
