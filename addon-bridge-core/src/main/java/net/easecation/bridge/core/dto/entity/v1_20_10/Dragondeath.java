package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Controls the entity's death state and animation. Note: This behavior can only be used by the ender_dragon entity type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dragondeath(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
