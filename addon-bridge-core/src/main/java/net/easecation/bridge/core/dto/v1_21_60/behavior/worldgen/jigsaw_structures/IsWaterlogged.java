package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests if the subject block is submerged in water. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsWaterlogged(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* true or false. */
    @JsonProperty("value") Boolean value
) {
}
