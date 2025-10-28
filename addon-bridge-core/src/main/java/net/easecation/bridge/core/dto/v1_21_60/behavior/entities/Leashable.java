package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows this entity to be leashed and defines the conditions and events for this entity when is leashed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Leashable(
    /* If true, players can leash this entity even if it is already leashed to another mob. */
    @JsonProperty("can_be_stolen") @Nullable Boolean canBeStolen,
    /* When set to true, "on_unleash" does not trigger when the entity gets unleashed for other reasons such as being stolen or the leash breaking. */
    @JsonProperty("on_unleash_interact_only") @Nullable Boolean onUnleashInteractOnly,
    @JsonProperty("hard_distance") @Nullable HardDistance hardDistance,
    @JsonProperty("max_distance") @Nullable MaximumDistance maxDistance,
    /* Event to call when this entity is leashed. */
    @JsonProperty("on_leash") @Nullable Event onLeash,
    /* Event to call when this entity is unleashed. */
    @JsonProperty("on_unleash") @Nullable Event onUnleash,
    @JsonProperty("soft_distance") @Nullable SoftDistance softDistance,
    /* Defines how this entity behaves when leashed to another entity. A preset is selected upon leashing and remains until the entity is leashed to something else. The first preset whose "filter" conditions are met will be applied; if none match, a default configuration is used instead. */
    @JsonProperty("presets") @Nullable List<Object> presets
) {
}
