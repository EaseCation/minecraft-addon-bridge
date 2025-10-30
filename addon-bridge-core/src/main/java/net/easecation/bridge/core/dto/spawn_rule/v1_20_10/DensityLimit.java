package net.easecation.bridge.core.dto.spawn_rule.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* This component allows the players to specify the amount of mobs to spawn in certain locations. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DensityLimit(
    /* This is the maximum number of mobs of this type spawnable on the surface. */
    @JsonProperty("surface") @Nullable Integer surface,
    /* This is the maximum number of mobs of this type spawnable underground. */
    @JsonProperty("underground") @Nullable Integer underground
) {
}
