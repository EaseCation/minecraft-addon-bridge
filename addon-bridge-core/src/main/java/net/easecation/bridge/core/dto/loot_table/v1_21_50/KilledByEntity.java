package net.easecation.bridge.core.dto.loot_table.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Returns the condition true if the actor of the loot table is killed by a specific entity type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record KilledByEntity(
    /* UNDOCUMENTED. */
    @JsonProperty("condition") @Nullable String condition,
    /* The entity type to match */
    @JsonProperty("entity_type") @Nullable String entityType
) {
}
