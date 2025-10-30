package net.easecation.bridge.core.dto.item.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Compels the item shoot, like an arrow.  Pair with minecraft:shooter for dispensers or as ammunition.  Pair with minecraft:throwable to set the entity spawned. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Projectile(
    /* How long you must charge a projectile for it to critically hit. */
    @JsonProperty("minimum_critical_power") @Nullable Double minimumCriticalPower,
    /* The entity to be fired as a projectile. */
    @JsonProperty("projectile_entity") String projectileEntity
) {
}
