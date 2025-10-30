package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A component that does damage to entities that get within range. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AreaAttack(
    /* The type of damage that is applied to entities that enter the damage range. */
    @JsonProperty("cause") @Nullable String cause,
    /* Attack cooldown (in seconds) for how often this entity can attack a target. */
    @JsonProperty("damage_cooldown") @Nullable Double damageCooldown,
    /* How much damage per tick is applied to entities that enter the damage range. */
    @JsonProperty("damage_per_tick") @Nullable Integer damagePerTick,
    /* How close a hostile entity must be to have the damage applied. */
    @JsonProperty("damage_range") @Nullable Double damageRange,
    /* The set of entities that are valid to apply the damage to when within range. */
    @JsonProperty("entity_filter") @Nullable Filters entityFilter,
    /* If the entity should play their attack sound when attacking a target. */
    @JsonProperty("play_attack_sound") @Nullable Boolean playAttackSound
) {
}
