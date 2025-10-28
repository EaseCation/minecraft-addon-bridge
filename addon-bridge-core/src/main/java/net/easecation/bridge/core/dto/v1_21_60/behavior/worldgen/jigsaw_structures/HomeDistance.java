package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the distance between the subject and its home. Returns false if the subject has no home or if their home is in a different dimension. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HomeDistance(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* (Required) A floating point value. */
    @JsonProperty("value") Double value
) {
}
