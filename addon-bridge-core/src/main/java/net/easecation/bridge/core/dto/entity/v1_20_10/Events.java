package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Events for entities. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Events(
    /* Event called on an entity that transforms into another entity. */
    @JsonProperty("minecraft:entity_transformed") @Nullable EfaEventBase minecraft_entityTransformed,
    /* Event called on an entity that is spawned through two entities breeding. */
    @JsonProperty("minecraft:entity_born") @Nullable EfaEventBase minecraft_entityBorn,
    /* Event called on an entity that is placed in the level. */
    @JsonProperty("minecraft:entity_spawned") @Nullable EfaEventBase minecraft_entitySpawned,
    /* Event called on an entity whose fuse is lit and is ready to explode. */
    @JsonProperty("minecraft:on_prime") @Nullable EfaEventBase minecraft_onPrime
) {
}
