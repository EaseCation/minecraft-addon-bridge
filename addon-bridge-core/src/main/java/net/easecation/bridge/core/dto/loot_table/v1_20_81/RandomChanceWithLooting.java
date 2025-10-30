package net.easecation.bridge.core.dto.loot_table.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sets a random chance of the specified value. Looting enchantment increase the random chance multiplier. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomChanceWithLooting(
    /* UNDOCUMENTED. */
    @JsonProperty("condition") @Nullable String condition,
    /* The random chance of the value. */
    @JsonProperty("chance") @Nullable Double chance,
    /* The multiplier for the chance if the target entity has the looting enchant that affects the actor. */
    @JsonProperty("looting_multiplier") @Nullable Double lootingMultiplier
) {
}
