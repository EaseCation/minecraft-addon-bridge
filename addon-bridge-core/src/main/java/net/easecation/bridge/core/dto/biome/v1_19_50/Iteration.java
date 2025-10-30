package net.easecation.bridge.core.dto.biome.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Iteration(
    /* The order in which coordinates will be evaluated. Should be used when a coordinate depends on another. If omitted, defaults to {@code xzy}. */
    @JsonProperty("coordinate_eval_order") @Nullable String coordinateEvalOrder,
    /* UNDOCUMENTED. */
    @JsonProperty("identifier") String identifier,
    /* Number of scattered positions to generate. */
    @JsonProperty("iterations") MolangNumber iterations,
    /* UNDOCUMENTED. */
    @JsonProperty("places_feature") String placesFeature,
    @JsonProperty("scatter_chance") @Nullable Object scatterChance,
    @JsonProperty("x") @Nullable ECoordinate x,
    @JsonProperty("y") @Nullable ECoordinate y,
    @JsonProperty("z") @Nullable ECoordinate z
) {
}
