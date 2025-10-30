package net.easecation.bridge.core.dto.spawn_rule.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This component lets players be filtered by whether they are in a village or not, using distance and the village border definitions. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerInVillageFilter(
    /* This is the maximum mob_event level that an entity spawns. */
    @JsonProperty("distance") @Nullable Integer distance,
    /* This is the minimum mob_event level that an entity spawns. */
    @JsonProperty("village_border_tolerance") @Nullable Integer villageBorderTolerance
) {
}
