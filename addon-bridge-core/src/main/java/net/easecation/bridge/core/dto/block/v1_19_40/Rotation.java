package net.easecation.bridge.core.dto.block.v1_19_40;

import com.fasterxml.jackson.annotation.*;

/* This is the block's rotation around the center of the cube in degrees. The rotation order is x-y-z. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Rotation(
) {
}
