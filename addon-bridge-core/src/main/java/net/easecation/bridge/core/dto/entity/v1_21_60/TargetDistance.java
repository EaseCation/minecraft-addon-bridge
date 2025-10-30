package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the distance between the calling entity and its target. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TargetDistance(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* (Required) A floating point value. */
    @JsonProperty("value") Double value
) {
}
