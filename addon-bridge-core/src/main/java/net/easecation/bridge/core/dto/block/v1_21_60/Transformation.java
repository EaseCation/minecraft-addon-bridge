package net.easecation.bridge.core.dto.block.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Supports rotation, scaling, and translation */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Transformation(
    /* Amount in degrees the block should be rotated on each axis. "rotation" is specified as [x, y, z] using floating point values and must be axis aligned, otherwise the value will be rounded to the nearest axis-aligned value. */
    @JsonProperty("rotation") @Nullable List<Double> rotation,
    /* Amount the block should be scaled along each axis. "scale" is specified as [x, y, z] using floating point values. */
    @JsonProperty("scale") @Nullable List<Double> scale,
    /* Amount the block should be translated along each axis. "translation" is specified as [x, y, z] using floating point values. */
    @JsonProperty("translation") @Nullable List<Double> translation,
    /* Offset to the pivot point around which to apply the scale. "scale_pivot" is specified as [x, y, z] using floating point values. */
    @JsonProperty("scale_pivot") @Nullable List<Double> scalePivot,
    /* Offset to the pivot point around which to apply the rotation. "rotation_pivot" is specified as [x, y, z] using floating point values. */
    @JsonProperty("rotation_pivot") @Nullable List<Double> rotationPivot
) {
}
