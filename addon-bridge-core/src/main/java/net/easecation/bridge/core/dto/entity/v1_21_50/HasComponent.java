package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject entity contains the named component. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasComponent(
    /* Returns true when the subject entity contains the named component. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* (Required) The component name to look for. */
    @JsonProperty("value") String value
) {
}
