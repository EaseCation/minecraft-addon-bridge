package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true when the subject entity is a member of the named family. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsFamily(
    /* Returns true when the subject entity is a member of the named family. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The Family name to look for. */
    @JsonProperty("value") String value
) {
}
