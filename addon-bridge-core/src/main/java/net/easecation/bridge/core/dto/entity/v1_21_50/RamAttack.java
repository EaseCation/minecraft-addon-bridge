package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to search for a random target and, if a direct path exists between the mob and the target, it will perform a charge. If the attack hits, the target will be knocked back based on the mob's speed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RamAttack(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The modifier to knockback that babies have. */
    @JsonProperty("baby_knockback_modifier") @Nullable Double babyKnockbackModifier,
    /* Minimum and maximum cooldown time-range (positive, in seconds) between each attempted ram attack. */
    @JsonProperty("cooldown_range") @Nullable Range_a_B cooldownRange,
    /* The force of the knockback of the ram attack. */
    @JsonProperty("knockback_force") @Nullable Double knockbackForce,
    /* The height of the knockback of the ram attack. */
    @JsonProperty("knockback_height") @Nullable Double knockbackHeight,
    /* The minimum distance at which the mob can start a ram attack. */
    @JsonProperty("min_ram_distance") @Nullable Double minRamDistance,
    /* The event to trigger when attacking. */
    @JsonProperty("on_start") @Nullable Trigger onStart,
    /* The sound to play when an entity is about to perform a ram attack. */
    @JsonProperty("pre_ram_sound") @Nullable String preRamSound,
    /* The distance at which the mob start to run with ram speed. */
    @JsonProperty("ram_distance") @Nullable Double ramDistance,
    /* The sound to play when an entity is impacting on a ram attack. */
    @JsonProperty("ram_impact_sound") @Nullable String ramImpactSound,
    /* Sets the entity's speed when charging toward the target. */
    @JsonProperty("ram_speed") @Nullable Double ramSpeed,
    /* Sets the entity's speed when running toward the target. */
    @JsonProperty("run_speed") @Nullable Double runSpeed,
    /* The event to trigger when attacking. */
    @JsonProperty("trigger") @Nullable Trigger trigger
) {
}
