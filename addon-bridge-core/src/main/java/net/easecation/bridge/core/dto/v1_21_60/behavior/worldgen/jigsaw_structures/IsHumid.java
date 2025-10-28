package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether the Subject is in an area with humidity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsHumid(
    /* Tests whether the Subject is in an area with humidity. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* True or false. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
