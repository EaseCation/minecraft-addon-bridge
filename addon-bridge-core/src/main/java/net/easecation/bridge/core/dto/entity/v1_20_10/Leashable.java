package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows this entity to be leashed and defines the conditions and events for this entity when is leashed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Leashable(
    /* If true, players can leash this entity even if it is already leashed to another mob. */
    @JsonProperty("can_be_stolen") @Nullable Boolean canBeStolen,
    /* Distance in blocks at which the leash stiffens, restricting movement. */
    @JsonProperty("hard_distance") @Nullable Double hardDistance,
    /* Distance in blocks at which the leash breaks. */
    @JsonProperty("max_distance") @Nullable Double maxDistance,
    /* Event to call when this entity is leashed. */
    @JsonProperty("on_leash") @Nullable Event onLeash,
    /* Event to call when this entity is unleashed. */
    @JsonProperty("on_unleash") @Nullable Event onUnleash,
    /* Distance in blocks at which the {@code spring} effect starts acting to keep this entity close to the entity that leashed it. */
    @JsonProperty("soft_distance") @Nullable Double softDistance
) {
}
