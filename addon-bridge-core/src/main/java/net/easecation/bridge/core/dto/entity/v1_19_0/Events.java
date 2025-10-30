package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Events for entities. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Events(
    /* Event called on an entity that transforms into another entity. */
    @JsonProperty("minecraft:entity_transformed") @Nullable EdgEventBase minecraft_entityTransformed,
    /* Event called on an entity that is spawned through two entities breeding. */
    @JsonProperty("minecraft:entity_born") @Nullable EdgEventBase minecraft_entityBorn,
    /* Event called on an entity that is placed in the level. */
    @JsonProperty("minecraft:entity_spawned") @Nullable EdgEventBase minecraft_entitySpawned,
    /* Event called on an entity whose fuse is lit and is ready to explode. */
    @JsonProperty("minecraft:on_prime") @Nullable EdgEventBase minecraft_onPrime
) {
}
