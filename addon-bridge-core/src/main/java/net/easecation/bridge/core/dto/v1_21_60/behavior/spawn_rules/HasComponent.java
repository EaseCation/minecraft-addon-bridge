package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject entity contains the named component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasComponent(
    /* Returns true when the subject entity contains the named component. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* (Required) The component name to look for. */
    @JsonProperty("value") String value
) {
}
