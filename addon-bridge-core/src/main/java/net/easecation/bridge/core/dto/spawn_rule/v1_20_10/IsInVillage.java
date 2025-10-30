package net.easecation.bridge.core.dto.spawn_rule.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether the Subject is inside the bounds of a village. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsInVillage(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
