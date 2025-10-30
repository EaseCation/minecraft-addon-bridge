package net.easecation.bridge.core.dto.trading.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns the condition true if the actor's mark variant is matched to the value. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record HasMarkVariant(
    /* Returns the condition true if the actor's mark variant is matched to the value. */
    @JsonProperty("condition") @Nullable String condition,
    /* Tests for the actor's mark variant (if it has one). */
    @JsonProperty("value") @Nullable Integer value
) {
}
