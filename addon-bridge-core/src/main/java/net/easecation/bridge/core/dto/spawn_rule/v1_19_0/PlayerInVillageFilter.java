package net.easecation.bridge.core.dto.spawn_rule.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerInVillageFilter(
    /* UNDOCUMENTED. */
    @JsonProperty("distance") @Nullable Integer distance,
    /* UNDOCUMENTED. */
    @JsonProperty("village_border_tolerance") @Nullable Integer villageBorderTolerance
) {
}
