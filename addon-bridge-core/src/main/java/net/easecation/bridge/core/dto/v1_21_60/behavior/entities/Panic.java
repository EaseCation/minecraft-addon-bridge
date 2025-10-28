package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to enter the panic state, which makes it run around and away from the damage source that made it enter this state. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Panic(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* The list of Entity Damage Sources that will cause this mob to panic. */
    @JsonProperty("damage_sources") @Nullable List<EntityDamageSource> damageSources,
    /* If true, this mob will not stop panicking until it can't move anymore or the goal is removed from it. */
    @JsonProperty("force") @Nullable Boolean force,
    /* If true, the mob will not panic in response to damage from other mobs. This overrides the damage types in {@code damage_sources} */
    @JsonProperty("ignore_mob_damage") @Nullable Boolean ignoreMobDamage,
    /* If true, the mob will prefer water over land. */
    @JsonProperty("prefer_water") @Nullable Boolean preferWater,
    /* The sound event to play when this mob is in panic. */
    @JsonProperty("panic_sound") @Nullable String panicSound,
    /* The range of time in seconds to randomly wait before playing the sound again. */
    @JsonProperty("sound_interval") @Nullable SoundInterval soundInterval
) {
    
        /* The range of time in seconds to randomly wait before playing the sound again. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record SoundInterval(
            /* The minimum time in seconds before the {@code panic_sound} plays. */
            @JsonProperty("range_min") @Nullable Double rangeMin,
            /* The maximum time in seconds before the {@code panic_sound} plays. */
            @JsonProperty("range_max") @Nullable Double rangeMax
        ) {
        }
}
