package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the rules for a mob to be tamed by the player. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Tameable(
    /* The chance of taming the entity with each item use between 0.0 and 1.0, where 1.0 is 100% */
    @JsonProperty("probability") @Nullable Double probability,
    /* Event to run when this entity becomes tamed. */
    @JsonProperty("tame_event") @Nullable Event tameEvent,
    /* The list of items that can be used to tame this entity. */
    @JsonProperty("tame_items") @Nullable Object tameItems
) {
}
