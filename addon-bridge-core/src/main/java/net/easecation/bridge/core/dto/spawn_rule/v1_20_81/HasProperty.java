package net.easecation.bridge.core.dto.spawn_rule.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests for the presence of a property of the subject entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasProperty(
    /* Tests for the presence of a property of the subject entity. */
    @JsonProperty("test") @Nullable String test,
    /* (Optionall) The comparison to apply with {@code value}. */
    @JsonProperty("operator") @Nullable String operator,
    /* (Optional) The subject of this filter test. */
    @JsonProperty("subject") @Nullable String subject,
    /* (Required) The property name to look for. */
    @JsonProperty("value") String value
) {
}
