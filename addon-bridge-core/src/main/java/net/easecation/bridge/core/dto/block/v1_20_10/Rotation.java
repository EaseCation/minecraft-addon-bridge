package net.easecation.bridge.core.dto.block.v1_20_10;

import com.fasterxml.jackson.annotation.*;

/* The block's rotation around the center of the cube in degrees. The rotation order is [x, y, z]. Angles need to be in multiples of 90. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Rotation(
) {
}
