package net.easecation.bridge.core.dto.trading.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns the condition true if the actor of the loot table is killed by player or entities that has owner. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record KilledByPlayerOrPetsOrPets(
    /* UNDOCUMENTED. */
    @JsonProperty("condition") @Nullable String condition
) {
}
