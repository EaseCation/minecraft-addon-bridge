package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests whether the biome the subject is in has the specified tag. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasBiomeTag(
    /* Tests whether the biome the subject is in has the specified tag. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The tag to look for. */
    @JsonProperty("value") String value
) {
}
