package net.easecation.bridge.core.dto.block.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A single material instance. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MaterialInstance(
    /* UNDOCUMENTED. */
    @JsonProperty("ambient_occlusion") @Nullable Boolean ambientOcclusion,
    /* UNDOCUMENTED. */
    @JsonProperty("face_dimming") @Nullable Boolean faceDimming,
    /* UNDOCUMENTED. */
    @JsonProperty("render_method") @Nullable String renderMethod,
    /* UNDOCUMENTED. */
    @JsonProperty("texture") @Nullable String texture
) {
}
