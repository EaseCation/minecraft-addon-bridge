package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sets the offset from the ground that the entity is actually at. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GroundOffset(
    /* The value of the entity's offset from the terrain, in blocks. */
    @JsonProperty("value") @Nullable Double value
) {
}
