package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests for the presence of a property of the subject entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasProperty(
    /* Tests for the presence of a property of the subject entity. */
    @JsonProperty("test") @Nullable String test,
    /* (Optionall) The comparison to apply with {@code value}. */
    @JsonProperty("operator") @Nullable Operator operator,
    /* (Optional) The subject of this filter test. */
    @JsonProperty("subject") @Nullable Subject subject,
    /* (Required) The property name to look for. */
    @JsonProperty("value") String value
) {
}
