package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sets the width and height of the Entity's collision box. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CollisionBox(
    /* Height of the collision box in blocks. A negative value will be assumed to be 0 */
    @JsonProperty("height") @Nullable Double height,
    /* Width and Depth of the collision box in blocks. A negative value will be assumed to be 0 */
    @JsonProperty("width") @Nullable Double width
) {
}
