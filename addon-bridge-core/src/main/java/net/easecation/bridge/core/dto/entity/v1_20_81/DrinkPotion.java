package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to drink potions based on specified environment conditions. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DrinkPotion(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Movement speed modifier of the mob when using this AI Goal. */
    @JsonProperty("speed_modifier") @Nullable Object speedModifier,
    /* A list of potions that this entity can drink. */
    @JsonProperty("potions") @Nullable List<Object> potions
) {
}
