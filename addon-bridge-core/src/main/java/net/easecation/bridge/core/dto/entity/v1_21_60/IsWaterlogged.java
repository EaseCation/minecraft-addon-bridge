package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests if the subject block is submerged in water. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsWaterlogged(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* true or false. */
    @JsonProperty("value") Boolean value
) {
}
