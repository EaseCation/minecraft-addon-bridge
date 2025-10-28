package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Control how this biome is instantiated (and then potentially modified) during world generation of the overworld. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OverworldGenerationRules(
    /* What biome to switch to when converting to a hilly biome. */
    @JsonProperty("hills_transformation") @Nullable BcTransformation hillsTransformation,
    /* What biome to switch to when converting to a mutated biome. */
    @JsonProperty("mutate_transformation") @Nullable BcTransformation mutateTransformation,
    /* What biome to switch to when converting to a river biome (if not the Vanilla 'river' biome). */
    @JsonProperty("river_transformation") @Nullable BcTransformation riverTransformation,
    /* What biome to switch to when adjacent to an ocean biome. */
    @JsonProperty("shore_transformation") @Nullable BcTransformation shoreTransformation,
    /* Controls the world generation climate categories that this biome can spawn for. A single biome can be associated with multiple categories with different weightings. */
    @JsonProperty("generate_for_climates") @Nullable List<List<Object>> generateForClimates
) {
}
