package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Marks the entity as being able to fly, the pathfinder won't be restricted to paths where a solid block is required underneath it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CanFly(
    /* Marks the entity as being able to fly, the pathfinder won't be restricted to paths where a solid block is required underneath it. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
