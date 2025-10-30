package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the dragon to go out with glory. This controls the Ender Dragon's death animation and can't be used by other mobs. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dragondeath(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
