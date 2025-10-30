package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to move around randomly like the Vex. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VexRandomMove(
    @JsonProperty("priority") @Nullable Integer priority,
    /* List of entities this mob can copy the owner from. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes
) {
}
