package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BggInteractionSpec(
    /* Loot table with items to add to the player's inventory upon successful interaction. */
    @JsonProperty("add_items") @Nullable AddItems addItems,
    /* Time in seconds before this entity can be interacted with again. */
    @JsonProperty("cooldown") @Nullable Double cooldown,
    /* Allows entity to admire the item. Requires "minecraft:admire<i>item" and "minecraft:behavior.admire</i>item" to work. */
    @JsonProperty("admire") @Nullable Boolean admire,
    /* Allows entity to barter with the item. Requires "minecraft:barter" to work. */
    @JsonProperty("barter") @Nullable Boolean barter,
    /* Time in seconds before this entity can be interacted with after being attacked. */
    @JsonProperty("cooldown_after_being_attacked") @Nullable Double cooldownAfterBeingAttacked,
    /* The amount of health this entity will recover or hurt when interacting with this item. Negative values will harm the entity. */
    @JsonProperty("health_amount") @Nullable Integer healthAmount,
    /* The amount of damage the item will take when used to interact with this entity. A value of 0 means the item won't lose durability. */
    @JsonProperty("hurt_item") @Nullable Integer hurtItem,
    /* Text to show when the player is able to interact in this way with this entity when playing with Touch-screen controls. */
    @JsonProperty("interact_text") @Nullable String interactText,
    /* Event to fire when the interaction occurs. */
    @JsonProperty("on_interact") @Nullable Trigger onInteract,
    /* Particle effect that will be triggered at the start of the interaction. */
    @JsonProperty("particle_on_start") @Nullable ParticleOnStart particleOnStart,
    /* List of sounds to play when the interaction occurs. */
    @JsonProperty("play_sounds") @Nullable String playSounds,
    /* List of entities to spawn when the interaction occurs. */
    @JsonProperty("spawn_entities") @Nullable String spawnEntities,
    /* Loot table with items to drop on the ground upon successful interaction. */
    @JsonProperty("spawn_items") @Nullable SpawnItems spawnItems,
    /* If true, the player will do the "swing" animation when interacting with this entity. */
    @JsonProperty("swing") @Nullable Boolean swing,
    /* The feed item used will transform to this item upon successful interaction. Format: itemName:auxValue */
    @JsonProperty("transform_to_item") @Nullable String transformToItem,
    /* If true, the interaction will use an item. */
    @JsonProperty("use_item") @Nullable Boolean useItem,
    /* Vibration to emit when the interaction occurs. Admitted values are entity_interact (used by default), shear, and none (no vibration emitted). */
    @JsonProperty("vibration") @Nullable String vibration,
    /* UNDOCUMENTED Item to give to the player upon successful interaction. */
    @JsonProperty("give_item") @Nullable Boolean giveItem,
    /* UNDOCUMENTED Takes an item from the player. */
    @JsonProperty("take_item") @Nullable Boolean takeItem
) {
    
        /* Loot table with items to add to the player's inventory upon successful interaction. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record AddItems(
            /* File path, relative to the Behavior Pack's path, to the loot table file. */
            @JsonProperty("table") @Nullable String table
        ) {
        }
    
        /* Particle effect that will be triggered at the start of the interaction. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record ParticleOnStart(
            /* Whether or not the particle will appear closer to who performed the interaction. */
            @JsonProperty("particle_offset_towards_interactor") @Nullable Boolean particleOffsetTowardsInteractor,
            /* The type of particle that will be spawned. */
            @JsonProperty("particle_type") @Nullable String particleType,
            /* Will offset the particle this amount in the y direction. */
            @JsonProperty("particle_y_offset") @Nullable Double particleYOffset
        ) {
        }
    
        /* Loot table with items to drop on the ground upon successful interaction. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record SpawnItems(
            /* File path, relative to the Behavior Pack's path, to the loot table file. */
            @JsonProperty("table") @Nullable String table
        ) {
        }
}
