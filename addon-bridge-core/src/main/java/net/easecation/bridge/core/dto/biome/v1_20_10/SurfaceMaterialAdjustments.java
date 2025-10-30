package net.easecation.bridge.core.dto.biome.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Specify fine-detail changes to blocks used in terrain generation (based on a noise function). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SurfaceMaterialAdjustments(
    /* All adjustments that match the column's noise values will be applied in the order listed. */
    @JsonProperty("adjustments") @Nullable Object adjustments
) {
}
