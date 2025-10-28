package net.easecation.bridge.core.dto.v1_21_60.behavior.loot_tables;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns the condition true if the actor of the loot table is killed by player or entities that has owner. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record KilledByPlayer(
    /* UNDOCUMENTED. */
    @JsonProperty("condition") @Nullable String condition
) {
}
