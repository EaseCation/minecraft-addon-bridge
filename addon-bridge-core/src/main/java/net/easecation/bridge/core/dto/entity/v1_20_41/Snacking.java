package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to take a load off and snack on food that it found nearby. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Snacking(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Items that we are interested in snacking on. */
    @JsonProperty("items") @Nullable Object items,
    /* The cooldown time in seconds before the mob is able to snack again. */
    @JsonProperty("snacking_cooldown") @Nullable Double snackingCooldown,
    /* The minimum time in seconds before the mob is able to snack again. */
    @JsonProperty("snacking_cooldown_min") @Nullable Double snackingCooldownMin,
    /* This is the chance that the mob will stop snacking, from 0 to 1. */
    @JsonProperty("snacking_stop_chance") @Nullable Double snackingStopChance
) {
}
