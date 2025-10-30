package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the entity's ranged attack behavior. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Shooter(
    /* ID of the Potion effect to be applied on hit. */
    @JsonProperty("aux_val") @Nullable Integer auxVal,
    /* Actor definition to use as projectile for the ranged attack. The actor definition must have the projectile component to be able to be shot as a projectile */
    @JsonProperty("def") @Nullable String def,
    /* Sets whether the projectiles being used are flagged as magic. If set, the ranged attack goal will not be used at the same time as other magic goals, such as minecraft:behavior.drink_potion. */
    @JsonProperty("magic") @Nullable Boolean magic,
    /* Velocity in which the projectiles will be shot. A power of 0 will be overwritten by the default projectile throw power. */
    @JsonProperty("power") @Nullable Double power,
    /* List of projectiles that can be used by the shooter. Projectiles are evaluated in the order of the list; after a projectile is chosen, the rest of the list is ignored. */
    @JsonProperty("projectiles") @Nullable List<String> projectiles,
    /* Sound that is played when the shooter shoots a projectile. */
    @JsonProperty("sound") @Nullable String sound
) {
}
