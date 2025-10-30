package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows a custom movement speed across lava blocks. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LavaMovement(
    /* The speed a strider moves over a lava block. */
    @JsonProperty("value") @Nullable Double value
) {
}
