package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the entity follow another entity. Both entities must be swimming and in water. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SwimWithEntity(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Percent chance to start following another entity, if not already doing so. 1.0 = 100% */
    @JsonProperty("success_rate") @Nullable Double successRate,
    /* Percent chance to stop following the current entity, if they're riding another entity or they're not swimming. 1.0 = 100% */
    @JsonProperty("chance_to_stop") @Nullable Double chanceToStop,
    /* Time (in seconds) between checks to determine if this entity should catch up to the entity being followed or match the direction of the entity being followed. */
    @JsonProperty("state_check_interval") @Nullable Double stateCheckInterval,
    /* Distance, from the entity being followed, at which this entity will speed up to reach that entity. */
    @JsonProperty("catch_up_threshold") @Nullable Double catchUpThreshold,
    /* Distance, from the entity being followed, at which this entity will try to match that entity's direction. */
    @JsonProperty("match_direction_threshold") @Nullable Double matchDirectionThreshold,
    /* The multiplier this entity's speed is modified by when matching another entity's direction. */
    @JsonProperty("catch_up_multiplier") @Nullable Double catchUpMultiplier,
    /* Radius around this entity to search for another entity to follow. */
    @JsonProperty("search_range") @Nullable Double searchRange,
    /* Distance, from the entity being followed, at which this entity will stop following that entity. */
    @JsonProperty("stop_distance") @Nullable Double stopDistance,
    /* Filters which determine what entites are valid to follow. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes
) {
}
