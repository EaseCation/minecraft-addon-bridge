package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* List of triggers with the events to call when taking specific kinds of damage. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Triggers(
    /* Type of damage that triggers the events. */
    @JsonProperty("cause") @Nullable String cause,
    /* A modifier that adds to/removes from the base damage from the damage cause. It does not reduce damage to less than 0. */
    @JsonProperty("damage_modifier") @Nullable Double damageModifier,
    /* A multiplier that modifies the base damage from the damage cause. If deals_damage is true the multiplier can only reduce the damage the entity will take to a minimum of 1. */
    @JsonProperty("damage_multiplier") @Nullable Double damageMultiplier,
    /*
 * Defines how received damage affects the entity:
 * - 'yes', received damage is applied to the entity.
 * - 'no', received damage is not applied to the entity.
 * - 'no<i>but</i>side<i>effects</i>apply', received damage is not applied to the entity, but the side effects of the attack are. This means that the attacker's weapon loses durability, enchantment side effects are applied, and so on.
 */
    @JsonProperty("deals_damage") @Nullable String dealsDamage,
    /* Specifies filters for entity definitions and events. */
    @JsonProperty("on_damage") @Nullable Trigger onDamage,
    /* Defines what sound to play, if any, when the on_damage filters are met. */
    @JsonProperty("on_damage_sound_event") @Nullable String onDamageSoundEvent
) {
}
