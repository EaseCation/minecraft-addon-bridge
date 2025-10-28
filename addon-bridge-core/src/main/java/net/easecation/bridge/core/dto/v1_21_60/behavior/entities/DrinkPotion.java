package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to drink potions based on specified environment conditions. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DrinkPotion(
    @JsonProperty("priority") @Nullable Priority priority,
    @JsonProperty("speed_multiplier") @Nullable SpeedMultiplier speedMultiplier,
    /* Movement speed modifier of the mob when using this AI Goal. */
    @JsonProperty("speed_modifier") @Nullable Object speedModifier,
    /* A list of potions that this entity can drink. */
    @JsonProperty("potions") @Nullable List<Object> potions
) {
}
