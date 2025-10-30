package net.easecation.bridge.core.dto.animation_controller.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ParticleEffectSpec(
    /* Set to false to have the effect spawned in the world without being bound to an actor (by default an effect is bound to the actor). */
    @JsonProperty("bind_to_actor") @Nullable Boolean bindToActor,
    /* The name of a particle effect that should be played. */
    @JsonProperty("effect") String effect,
    /* The name of a locator on the actor where the effect should be located. */
    @JsonProperty("locator") @Nullable String locator,
    /* A molang script that will be run when the particle emitter is initialized. */
    @JsonProperty("pre_effect_script") @Nullable String preEffectScript
) {
}
