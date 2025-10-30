package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Adds a rider to the entity. Requires {@code minecraft:rideable.} */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AddRider(
    /* The entity type that will be riding this entity. */
    @JsonProperty("entity_type") String entityType,
    /* The spawn event that will be used when the riding entity is created. */
    @JsonProperty("spawn_event") @Nullable String spawnEvent
) {
}
