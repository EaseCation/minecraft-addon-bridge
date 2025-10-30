package net.easecation.bridge.core.dto.biome.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Control how this biome is instantiated (and then potentially modified) during world generation of the overworld. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OverworldGenerationRules(
    /* UNDOCUMENTED. */
    @JsonProperty("hills_transformation") @Nullable BfTransformation hillsTransformation,
    /* UNDOCUMENTED. */
    @JsonProperty("mutate_transformation") @Nullable BfTransformation mutateTransformation,
    /* UNDOCUMENTED. */
    @JsonProperty("river_transformation") @Nullable BfTransformation riverTransformation,
    /* UNDOCUMENTED. */
    @JsonProperty("shore_transformation") @Nullable BfTransformation shoreTransformation,
    /* Controls the world generation climate categories that this biome can spawn for.  A single biome can be associated with multiple categories with different weightings. */
    @JsonProperty("generate_for_climates") @Nullable List<List<Object>> generateForClimates
) {
}
