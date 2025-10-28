package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests if the subject is a surface mob. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SurfaceMob(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
