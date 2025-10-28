package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to target the same entity its owner is targeting. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VexCopyOwnerTarget(
    @JsonProperty("priority") @Nullable Priority priority,
    /* List of entities this mob can copy the owner from. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes
) {
}
