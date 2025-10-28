package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Entities with this component will have an immunity to the provided mob effects. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MobEffectImmunity(
    /* List of names of effects the entity is immune to. */
    @JsonProperty("mob_effects") @Nullable List<Effect> mobEffects
) {
}
