package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns true if the subject entity is the skin id number provided. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsSkinId(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The altitude value to compare with. */
    @JsonProperty("value") @Nullable Integer value
) {
}
