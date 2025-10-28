package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the way an entity can get into the 'bribed' state. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Bribeable(
    /* Time in seconds before the Entity can be bribed again. */
    @JsonProperty("bribe_cooldown") @Nullable Double bribeCooldown,
    /* The list of items that can be used to bribe the entity. */
    @JsonProperty("bribe_items") @Nullable List<EntitiesBb> bribeItems
) {
}
