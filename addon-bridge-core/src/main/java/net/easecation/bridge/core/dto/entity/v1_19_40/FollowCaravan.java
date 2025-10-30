package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to follow mobs that are in a caravan. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FollowCaravan(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* List of entity types that this mob can follow in a caravan. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes,
    /* Number of entities that can be in the caravan. */
    @JsonProperty("entity_count") @Nullable Integer entityCount
) {
}
