package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the subject entity is the mark variant number provided. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsMarkVariant(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The altitude value to compare with. */
    @JsonProperty("value") @Nullable Integer value
) {
}
