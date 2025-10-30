package net.easecation.bridge.core.dto.block.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Supports rotation, scaling, and translation */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Transformation(
    /* Rotation in xxx? */
    @JsonProperty("rotation") @Nullable List<Double> rotation,
    /* UNDOCUMENTED */
    @JsonProperty("scale") @Nullable List<Double> scale,
    /* UNDOCUMENTED */
    @JsonProperty("translation") @Nullable List<Double> translation
) {
}
