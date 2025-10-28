package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether the Subject has the specified mob effect. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasMobEffect(
    /* Tests whether the Subject has the specified mob effect. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The specified mob effect. */
    @JsonProperty("value") Effect value
) {
}
