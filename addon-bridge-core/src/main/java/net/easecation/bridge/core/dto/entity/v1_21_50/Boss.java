package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The current state of the boss for updating the boss HUD. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Boss(
    /* The Maximum distance from the boss at which the boss's health bar is present on the players screen. */
    @JsonProperty("hud_range") @Nullable Integer hudRange,
    /* The name that will be displayed above the boss's health bar. */
    @JsonProperty("name") @Nullable String name,
    /* Whether the sky should darken in the presence of the boss. */
    @JsonProperty("should_darken_sky") @Nullable Boolean shouldDarkenSky
) {
}
