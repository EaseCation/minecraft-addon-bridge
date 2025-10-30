package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Adds a timer after which this entity will spawn another entity or item (similar to vanilla's chicken's egg-laying behavior). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpawnEntity(
    /* The entities to spawn. */
    @JsonProperty("entities") @Nullable Object entities
) {
}
