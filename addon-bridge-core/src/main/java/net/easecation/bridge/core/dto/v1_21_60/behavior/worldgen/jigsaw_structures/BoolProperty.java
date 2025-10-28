package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the bool actor property matches the value provided. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BoolProperty(
    /* Returns true when the bool actor property matches the value provided. */
    @JsonProperty("test") @Nullable String test,
    /* (Required) The property name to look for */
    @JsonProperty("domain") String domain,
    /* (Optional) The comparison to apply with {@code value}. */
    @JsonProperty("operator") @Nullable Operator operator,
    /* (Optional) The subject of this filter test. */
    @JsonProperty("subject") @Nullable Subject subject,
    /* true or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
