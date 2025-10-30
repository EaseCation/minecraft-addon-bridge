package net.easecation.bridge.core.dto.biome.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Noise parameters used to drive terrain height in the Overworld. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OverworldHeight(
    /* UNDOCUMENTED. */
    @JsonProperty("noise_params") @Nullable List<Double> noiseParams,
    /* UNDOCUMENTED. */
    @JsonProperty("noise_type") @Nullable String noiseType
) {
}
