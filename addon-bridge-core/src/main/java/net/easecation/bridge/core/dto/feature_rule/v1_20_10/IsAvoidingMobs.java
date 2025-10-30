package net.easecation.bridge.core.dto.feature_rule.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the subject entity is fleeing from other mobs. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsAvoidingMobs(
    /* Returns true if the subject entity is fleeing from other mobs. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
