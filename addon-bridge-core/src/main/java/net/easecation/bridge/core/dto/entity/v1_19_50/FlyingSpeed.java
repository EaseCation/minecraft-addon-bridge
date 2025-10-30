package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Speed in Blocks that this entity flies at. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FlyingSpeed(
    /* Flying speed in blocks per tick. */
    @JsonProperty("value") @Nullable Double value
) {
}
