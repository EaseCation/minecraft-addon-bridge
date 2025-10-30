package net.easecation.bridge.core.dto.spawn_rule.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the enum actor property matches the value provided. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EnumProperty(
    /* Returns true when the enum actor property matches the value provided. */
    @JsonProperty("test") @Nullable String test,
    /* (Required) The property name to look for */
    @JsonProperty("domain") String domain,
    /* (Optional) The comparison to apply with {@code value}. */
    @JsonProperty("operator") @Nullable String operator,
    /* (Optional) The subject of this filter test. */
    @JsonProperty("subject") @Nullable String subject,
    /* (Required) A string value. */
    @JsonProperty("value") String value
) {
}
