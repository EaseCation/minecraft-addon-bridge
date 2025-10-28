package net.easecation.bridge.core.dto.v1_21_60.behavior.blocks;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Required component to use the custom component {@code onEntityFallOn}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EntityFallOn(
    /* Sets the minimum fall distance required to trigger the custom component. */
    @JsonProperty("min_fall_distance") @Nullable Double minFallDistance
) {
}
