package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether the current temperature is a given type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsTarget(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The Biome temperature catagory to test. */
    @JsonProperty("value") String value
) {
}
