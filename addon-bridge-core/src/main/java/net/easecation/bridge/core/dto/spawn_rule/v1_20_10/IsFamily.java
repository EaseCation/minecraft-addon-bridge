package net.easecation.bridge.core.dto.spawn_rule.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject entity is a member of the named family. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsFamily(
    /* Returns true when the subject entity is a member of the named family. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable String operator,
    @JsonProperty("subject") @Nullable String subject,
    /* The Family name to look for. */
    @JsonProperty("value") String value
) {
}
