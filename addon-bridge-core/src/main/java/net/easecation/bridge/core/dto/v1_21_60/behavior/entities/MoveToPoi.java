package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to move to a POI if able to. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveToPoi(
    @JsonProperty("priority") @Nullable Priority priority,
    @JsonProperty("speed_multiplier") @Nullable SpeedMultiplier speedMultiplier,
    /* Tells the goal what POI type it should be looking for. */
    @JsonProperty("poi_type") @Nullable String poiType
) {
}
