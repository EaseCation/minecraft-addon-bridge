package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the squid to stick to the ground when outside water. Can only be used by the Squid. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SquidOutOfWater(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
