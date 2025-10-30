package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Despawns the Actor immediately. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InstantDespawn(
    /* If true, all entities linked to this entity in a child relationship (eg. leashed) will also be despawned. */
    @JsonProperty("remove_child_entities") @Nullable Boolean removeChildEntities
) {
}
