package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the entity's ranged attack behavior. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Shooter(
    /* ID of the Potion effect to be applied on hit. */
    @JsonProperty("aux_val") @Nullable Integer auxVal,
    /* Actor definition to use as projectile for the ranged attack. The actor definition must have the projectile component to be able to be shot as a projectile */
    @JsonProperty("def") @Nullable String def,
    /* UNDOCUMENTED. */
    @JsonProperty("type") @Nullable String type
) {
}
