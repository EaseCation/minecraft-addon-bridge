package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to play dead when attacked by other entities. When playing dead, other entities will not target this mob. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayDead(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Whether the mob will receive the regeneration effect while playing dead. */
    @JsonProperty("apply_regeneration") @Nullable Boolean applyRegeneration,
    /* The amount of time the mob will remain playing dead (in seconds). */
    @JsonProperty("duration") @Nullable Double duration,
    /* The list of other triggers that are required for the mob to activate play dead. */
    @JsonProperty("filters") @Nullable Filters filters,
    /* The amount of health at which damage will cause the mob to play dead. */
    @JsonProperty("force_below_health") @Nullable Integer forceBelowHealth,
    /* The likelihood of this goal starting upon taking damage. */
    @JsonProperty("random_start_chance") @Nullable Double randomStartChance,
    /* The range of damage that may cause the goal to start depending on randomness. Damage taken below the min will never cause the goal to start. Damage taken above the max will always cause the goal to start. */
    @JsonProperty("random_damage_range") @Nullable List<Integer> randomDamageRange,
    /* The list of Entity Damage Sources that will cause this mob to play dead. */
    @JsonProperty("damage_sources") @Nullable Object damageSources
) {
}
