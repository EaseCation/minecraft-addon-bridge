package net.easecation.bridge.core.dto.v1_21_60.behavior.worldgen.jigsaw_structures;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Tests the current weather, at the actor's position, against a provided weather value. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherAtPosition(
    /* The test property. */
    @JsonProperty("test") @Nullable String test,
    @JsonProperty("operator") @Nullable Operator operator,
    @JsonProperty("subject") @Nullable Subject subject,
    /* The Family name to look for. */
    @JsonProperty("value") String value
) {
}
