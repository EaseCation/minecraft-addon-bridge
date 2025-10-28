package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to target a mob that is hurt by their owner. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OwnerHurtTarget(
    @JsonProperty("priority") @Nullable Integer priority,
    /* List of entity types that this entity can target if the potential target is hurt by this mob's owner. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes
) {
}
