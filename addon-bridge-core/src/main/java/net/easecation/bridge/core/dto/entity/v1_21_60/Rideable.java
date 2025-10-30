package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Determines whether this entity can be ridden. Allows specifying the different seat positions and quantity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Rideable(
    /* The seat that designates the driver of the entity. */
    @JsonProperty("controlling_seat") @Nullable Integer controllingSeat,
    /* If true, this entity can't be interacted with if the entity interacting with it is crouching. */
    @JsonProperty("crouching_skip_interact") @Nullable Boolean crouchingSkipInteract,
    /* List of entities that can ride this entity. */
    @JsonProperty("family_types") @Nullable List<String> familyTypes,
    /* The text to display when the player can interact with the entity when playing with Touch-screen controls. */
    @JsonProperty("interact_text") @Nullable String interactText,
    /* The max width a mob can be to be a passenger. A value of 0 ignores this parameter. */
    @JsonProperty("passenger_max_width") @Nullable Double passengerMaxWidth,
    /* If true, this entity will pull in entities that are in the correct family_types into any available seats. */
    @JsonProperty("pull_in_entities") @Nullable Boolean pullInEntities,
    /* If true, this entity will be picked when looked at by the rider. */
    @JsonProperty("rider_can_interact") @Nullable Boolean riderCanInteract,
    /* The number of entities that can ride this entity at the same time. */
    @JsonProperty("seat_count") @Nullable Integer seatCount,
    /* The list of positions and number of riders for each position for entities riding this entity. */
    @JsonProperty("seats") @Nullable Object seats
) {
}
