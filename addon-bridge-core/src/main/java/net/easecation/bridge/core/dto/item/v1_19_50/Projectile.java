package net.easecation.bridge.core.dto.item.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Projectile item component. projectile items shoot out, like an arrow. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Projectile(
    /* How long you must charge a projectile for it to critically hit. */
    @JsonProperty("minimum_critical_power") @Nullable Double minimumCriticalPower,
    /* The entity to be fired as a projectile. */
    @JsonProperty("projectile_entity") String projectileEntity
) {
}
