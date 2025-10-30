package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to move towards the nearest underwater ruin or shipwreck. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FindUnderwaterTreasure(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* The range that the mob will search for a treasure chest within a ruin or shipwreck to move towards. */
    @JsonProperty("search_range") @Nullable Integer searchRange,
    /* The distance the mob will move before stopping. */
    @JsonProperty("stop_distance") @Nullable Double stopDistance
) {
}
