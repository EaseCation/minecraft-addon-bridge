package net.easecation.bridge.core.dto.biome.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Force specific decorative features (trees, plants, etc.) to appear in this Biome, regardless of normal decoration rules. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ForcedFeatures(
    /* UNDOCUMENTED. */
    @JsonProperty("after_sky_pass") @Nullable List<Iteration> afterSkyPass,
    /* UNDOCUMENTED. */
    @JsonProperty("after_surface_pass") @Nullable List<Iteration> afterSurfacePass,
    /* UNDOCUMENTED. */
    @JsonProperty("after_underground_pass") @Nullable List<Iteration> afterUndergroundPass,
    /* UNDOCUMENTED. */
    @JsonProperty("before_sky_pass") @Nullable List<Iteration> beforeSkyPass,
    /* UNDOCUMENTED. */
    @JsonProperty("before_surface_pass") @Nullable List<Iteration> beforeSurfacePass,
    /* UNDOCUMENTED. */
    @JsonProperty("before_underground_pass") @Nullable List<Iteration> beforeUndergroundPass,
    /* UNDOCUMENTED. */
    @JsonProperty("final_pass") @Nullable List<Iteration> finalPass,
    /* UNDOCUMENTED. */
    @JsonProperty("first_pass") @Nullable List<Iteration> firstPass,
    /* UNDOCUMENTED. */
    @JsonProperty("surface_pass") @Nullable List<Iteration> surfacePass,
    /* UNDOCUMENTED. */
    @JsonProperty("sky_pass") @Nullable List<Iteration> skyPass,
    /* UNDOCUMENTED. */
    @JsonProperty("underground_pass") @Nullable List<Iteration> undergroundPass
) {
}
