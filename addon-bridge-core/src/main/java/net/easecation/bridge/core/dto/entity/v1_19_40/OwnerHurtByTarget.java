package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to target another mob that hurts their owner. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OwnerHurtByTarget(
    @JsonProperty("priority") @Nullable Integer priority,
    /* List of entity types that this mob can target if they hurt their owner. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes
) {
}
