package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* [EXPERIMENTAL BEHAVIOR] Allows the entity to eat a specified Mob. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EatMob(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Sets the time in seconds the eat animation should play for. */
    @JsonProperty("eat_animation_time") @Nullable Double eatAnimationTime,
    /* Sets the sound that should play when eating a mob. */
    @JsonProperty("eat_mob_sound") @Nullable String eatMobSound,
    /* The loot table for loot to be dropped when eating a mob. */
    @JsonProperty("loot_table") @Nullable String lootTable,
    /* Sets the force which the mob-to-be-eaten is pulled towards the eating mob. */
    @JsonProperty("pull_in_force") @Nullable Double pullInForce,
    /* Sets the desired distance to be reached before eating the mob. */
    @JsonProperty("reach_mob_distance") @Nullable Double reachMobDistance,
    /* Sets the entity's speed when running toward the target. */
    @JsonProperty("run_speed") @Nullable Double runSpeed
) {
}
