package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the current temperature against a provided value in the range (0.0, 1.0) where 0.0f is the coldest temp and 1.0f is the hottest. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsTemperatureValue(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The Biome temperature value to compare with. */
    @JsonProperty("value") Double value
) {
}
