package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to give items it has to others. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ShareItems(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* List of entities this mob will share items with. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes,
    /* Distance in blocks within the mob considers it has reached the goal. This is the {@code wiggle room} to stop the AI from bouncing back and forth trying to reach a specific spot */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* Maximum distance in blocks this mob will look for entities to share items with. */
    @JsonProperty("max_dist") @Nullable Double maxDist
) {
}
