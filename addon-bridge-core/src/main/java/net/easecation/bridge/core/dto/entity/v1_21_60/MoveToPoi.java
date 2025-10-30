package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to move to a POI if able to. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveToPoi(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Tells the goal what POI type it should be looking for. */
    @JsonProperty("poi_type") @Nullable String poiType
) {
}
