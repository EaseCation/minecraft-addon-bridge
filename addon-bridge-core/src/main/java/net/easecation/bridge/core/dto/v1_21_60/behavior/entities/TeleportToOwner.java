package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows an entity to teleport to its owner. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TeleportToOwner(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The time in seconds that must pass for the entity to be able to try to teleport again. */
    @JsonProperty("cooldown") @Nullable Double cooldown,
    /* Conditions to be satisfied for the entity to teleport to its owner. */
    @JsonProperty("filters") @Nullable Filters filters
) {
}
