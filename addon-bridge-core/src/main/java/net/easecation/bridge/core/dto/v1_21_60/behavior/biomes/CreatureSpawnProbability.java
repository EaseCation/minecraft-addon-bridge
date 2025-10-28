package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Probability that creatures will spawn within the biome when a chunk is generated. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CreatureSpawnProbability(
    /* Probabiltity between [0.0, 0.75] of creatures spawning within the biome on chunk generation. */
    @JsonProperty("probability") @Nullable Double probability
) {
}
