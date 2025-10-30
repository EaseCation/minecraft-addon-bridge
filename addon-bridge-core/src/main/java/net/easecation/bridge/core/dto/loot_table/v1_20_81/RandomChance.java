package net.easecation.bridge.core.dto.loot_table.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sets a random chance of the specified value. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomChance(
    /* UNDOCUMENTED. */
    @JsonProperty("condition") @Nullable String condition,
    /* UNDOCUMENTED. */
    @JsonProperty("chance") @Nullable Double chance,
    /* The maximum random chance value allowed. */
    @JsonProperty("max_chance") @Nullable Double maxChance
) {
}
