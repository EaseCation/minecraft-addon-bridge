package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the subject entity leashed to the calling entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsLeashedTo(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
