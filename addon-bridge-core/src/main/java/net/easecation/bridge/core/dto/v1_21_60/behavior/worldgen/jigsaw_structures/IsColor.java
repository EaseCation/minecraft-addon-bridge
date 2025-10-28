package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the subject entity is the named color. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsColor(
    /* Returns true if the subject entity is the named color. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The Palette Color to test. */
    @JsonProperty("value") String value
) {
}
