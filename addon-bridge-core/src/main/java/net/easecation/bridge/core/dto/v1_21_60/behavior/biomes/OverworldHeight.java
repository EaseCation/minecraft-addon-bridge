package net.easecation.bridge.core.dto.v1_21_60.behavior.biomes;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Noise parameters used to drive terrain height in the Overworld. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OverworldHeight(
    /* Array of 2 Floats. */
    @JsonProperty("noise_params") @Nullable List<Double> noiseParams,
    /* Specifies a preset based on a built-in setting rather than manually using noise_params. */
    @JsonProperty("noise_type") @Nullable String noiseType
) {
}
