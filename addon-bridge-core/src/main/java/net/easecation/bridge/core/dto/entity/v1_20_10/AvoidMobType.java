package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the entity to run away from other entities that meet the criteria specified. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AvoidMobType(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The sound event to play when the mob is avoiding another mob. */
    @JsonProperty("avoid_mob_sound") @Nullable String avoidMobSound,
    /* The next target position the entity chooses to avoid another entity will be chosen within this XZ Distance. */
    @JsonProperty("avoid_target_xz") @Nullable Integer avoidTargetXz,
    /* The next target position the entity chooses to avoid another entity will be chosen within this Y Distance. */
    @JsonProperty("avoid_target_y") @Nullable Integer avoidTargetY,
    /* Whether or not to ignore direct line of sight while this entity is running away from other specified entities. */
    @JsonProperty("ignore_visibilty") @Nullable Boolean ignoreVisibilty,
    /* Maximum distance to look for an avoid target for the entity. */
    @JsonProperty("max_dist") @Nullable Double maxDist,
    /* How many blocks away from its avoid target the entity must be for it to stop fleeing from the avoid target. */
    @JsonProperty("max_flee") @Nullable Double maxFlee,
    /* Percent chance this entity will stop avoiding another entity based on that entity's strength, where 1.0 = 100%. */
    @JsonProperty("probability_per_strength") @Nullable Double probabilityPerStrength,
    /* Determine if we should remove target when fleeing or not. */
    @JsonProperty("remove_target") @Nullable Boolean removeTarget,
    /* How many blocks within range of its avoid target the entity must be for it to begin sprinting away from the avoid target. */
    @JsonProperty("sprint_distance") @Nullable Double sprintDistance,
    /* Multiplier for sprint speed. 1.0 means keep the regular speed, while higher numbers make the sprint speed faster. */
    @JsonProperty("sprint_speed_multiplier") @Nullable Double sprintSpeedMultiplier,
    /* Multiplier for walking speed. 1.0 means keep the regular speed, while higher numbers make the walking speed faster. */
    @JsonProperty("walk_speed_multiplier") @Nullable Double walkSpeedMultiplier,
    /* If true, visbility between this entity and the mob type will not be checked. */
    @JsonProperty("ignore_visibility") @Nullable Boolean ignoreVisibility,
    /* The list of conditions another entity must meet to be a valid target to avoid. */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes,
    /* Event that is triggered when escaping from a mob. */
    @JsonProperty("on_escape_event") @Nullable Event onEscapeEvent,
    /* The range of time in seconds to randomly wait before playing the sound again. */
    @JsonProperty("sound_interval") @Nullable Object soundInterval
) {
}
