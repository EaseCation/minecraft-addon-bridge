package net.easecation.bridge.core.dto.loot_table.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Transforms a normal map into a treasure map that marks the location of hidden treasure. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ExplorationMap(
    /* Transforms a normal map into a treasure map that marks the location of hidden treasure. */
    @JsonProperty("function") @Nullable String function,
    /* The destination value defines what type of treasure map they receive. */
    @JsonProperty("destination") @Nullable String destination
) {
}
