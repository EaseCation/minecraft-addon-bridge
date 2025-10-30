package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EntitySpawn(
    /* If present, the specified entity will only spawn if the filter evaluates to true. */
    @JsonProperty("filters") @Nullable Filters filters,
    /* Maximum amount of time to randomly wait in seconds before another entity is spawned. */
    @JsonProperty("max_wait_time") @Nullable Integer maxWaitTime,
    /* Minimum amount of time to randomly wait in seconds before another entity is spawned. */
    @JsonProperty("min_wait_time") @Nullable Integer minWaitTime,
    /* The number of entities of this type to spawn each time that this triggers. */
    @JsonProperty("num_to_spawn") @Nullable Integer numToSpawn,
    /* If true, this the spawned entity will be leashed to the parent. */
    @JsonProperty("should_leash") @Nullable Boolean shouldLeash,
    /* If true, this component will only ever spawn the specified entity once. */
    @JsonProperty("single_use") @Nullable Boolean singleUse,
    /* Identifier of the entity to spawn, leave empty to spawn the item defined above instead. */
    @JsonProperty("spawn_entity") @Nullable String spawnEntity,
    /* Event to call when the entity is spawned. */
    @JsonProperty("spawn_event") @Nullable String spawnEvent,
    /* Item identifier of the item to spawn. */
    @JsonProperty("spawn_item") @Nullable EntityBb spawnItem,
    /* Method to use to spawn the entity. */
    @JsonProperty("spawn_method") @Nullable String spawnMethod,
    /* Identifier of the sound effect to play when the entity is spawned. */
    @JsonProperty("spawn_sound") @Nullable String spawnSound,
    /* Event to call on this entity when the item is spawned. */
    @JsonProperty("spawn_item_event") @Nullable Event spawnItemEvent
) {
}
