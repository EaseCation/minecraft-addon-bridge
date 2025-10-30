package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Events for entities. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Events(
    /* Event called on an entity that transforms into another entity. */
    @JsonProperty("minecraft:entity_transformed") @Nullable EgaEventBase minecraft_entityTransformed,
    /* Event called on an entity that is spawned through two entities breeding. */
    @JsonProperty("minecraft:entity_born") @Nullable EgaEventBase minecraft_entityBorn,
    /* Event called on an entity that is placed in the level. */
    @JsonProperty("minecraft:entity_spawned") @Nullable EgaEventBase minecraft_entitySpawned,
    /* Event called on an entity whose fuse is lit and is ready to explode. */
    @JsonProperty("minecraft:on_prime") @Nullable EgaEventBase minecraft_onPrime
) {
}
