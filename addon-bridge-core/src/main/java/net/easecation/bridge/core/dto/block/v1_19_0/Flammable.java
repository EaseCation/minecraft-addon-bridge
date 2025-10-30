package net.easecation.bridge.core.dto.block.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Describes the flammable properties for this block. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Flammable(
    /* How likely the block will be destroyed by flames when on fire. Value must be greater than or equal to 0. */
    @JsonProperty("burn_odds") @Nullable Integer burnOdds,
    /* How likely the block will catch flame when next to a fire. Value must be greater than or equal to 0. */
    @JsonProperty("flame_odds") @Nullable Integer flameOdds
) {
}
