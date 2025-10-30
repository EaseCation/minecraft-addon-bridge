package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines what blocks this entity can breathe in and gives them the ability to suffocate. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Breathable(
    /* Time in seconds the entity can hold its breath. */
    @JsonProperty("total_supply") @Nullable Integer totalSupply,
    /* Time in seconds between suffocation damage. */
    @JsonProperty("suffocate_time") @Nullable Integer suffocateTime,
    /* Time in seconds to recover breath to maximum. */
    @JsonProperty("inhale_time") @Nullable Double inhaleTime,
    /* If true, this entity can breathe in air. */
    @JsonProperty("breathes_air") @Nullable Boolean breathesAir,
    /* If true, this entity can breathe in water. */
    @JsonProperty("breathes_water") @Nullable Boolean breathesWater,
    /* If true, this entity can breathe in lava. */
    @JsonProperty("breathes_lava") @Nullable Boolean breathesLava,
    /* If true, this entity can breathe in solid blocks. */
    @JsonProperty("breathes_solids") @Nullable Boolean breathesSolids,
    /* If true, this entity will have visible bubbles while in water. */
    @JsonProperty("generates_bubbles") @Nullable Boolean generatesBubbles,
    /* List of blocks this entity can breathe in, in addition to the above. */
    @JsonProperty("breathe_blocks") @Nullable List<BlockReference> breatheBlocks,
    /* List of blocks this entity can't breathe in, in addition to the above. */
    @JsonProperty("non_breathe_blocks") @Nullable List<BlockReference> nonBreatheBlocks
) {
}
