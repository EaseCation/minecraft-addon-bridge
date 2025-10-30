package net.easecation.bridge.core.dto.spawn_rule.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This component lets players be filtered by whether they are in a village or not, using distance and the village border definitions. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerInVillageFilter(
    /* UNDOCUMENTED. */
    @JsonProperty("distance") @Nullable Integer distance,
    /* UNDOCUMENTED. */
    @JsonProperty("village_border_tolerance") @Nullable Integer villageBorderTolerance
) {
}
