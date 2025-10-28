package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* allows the entity to have a balloon attached and defines the conditions and events for the entity when is ballooned. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Balloonable(
    /* Distance in blocks where the 'spring' effect lifts the entity. */
    @JsonProperty("soft_distance") @Nullable Double softDistance,
    /* Distance in blocks where the balloon breaks. */
    @JsonProperty("max_distance") @Nullable Double maxDistance,
    /* Event to call when the entity is ballooned. */
    @JsonProperty("on_balloon") @Nullable String onBalloon,
    /* Event to call when the entity is unballooned. */
    @JsonProperty("on_unballoon") @Nullable String onUnballoon,
    /* Mass that the entity has when computing balloon pull forces. */
    @JsonProperty("mass") @Nullable Double mass
) {
}
