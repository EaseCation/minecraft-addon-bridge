package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* [EXPERIMENTAL BEHAVIOR] The entity will attempt to toss the items from its inventory to a nearby recently played noteblock. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StayNearNoteblock(
    @JsonProperty("priority") @Nullable Priority priority,
    /* Sets the time an entity should stay near a noteblock after hearing it. */
    @JsonProperty("listen_time") @Nullable Integer listenTime,
    /* Sets the entity's speed when moving toward the block. */
    @JsonProperty("speed") @Nullable Double speed,
    /* Sets the distance the entity needs to be away from the block to attempt to start the goal. */
    @JsonProperty("start_distance") @Nullable Double startDistance,
    /* Sets the distance from the block the entity will attempt to reach. */
    @JsonProperty("stop_distance") @Nullable Double stopDistance
) {
}
