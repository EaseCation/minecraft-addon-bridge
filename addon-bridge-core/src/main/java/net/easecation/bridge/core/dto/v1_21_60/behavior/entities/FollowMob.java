package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to follow other mobs. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FollowMob(
    @JsonProperty("priority") @Nullable Integer priority,
    /* If non-empty, provides criteria for filtering which nearby Mobs can be followed. If empty default criteria will be used, which will exclude Players, Squid variants, Fish variants, Tadpoles, Dolphins, and mobs of the same type as the owner of the Goal. */
    @JsonProperty("filters") @Nullable Filters filters,
    /* The type of actor to prefer following. If left unspecified, a random actor among those in range will be chosen. */
    @JsonProperty("preferred_actor_type") @Nullable String preferredActorType,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* The distance in blocks it will look for a mob to follow. */
    @JsonProperty("search_range") @Nullable Integer searchRange,
    /* The distance in blocks this mob stops from the mob it is following. */
    @JsonProperty("stop_distance") @Nullable Double stopDistance,
    /* If true, the mob will respect the 'minecraft:home' component's 'restriction_radius' field when choosing a target to follow. If false, it will choose target position without considering home restrictions. */
    @JsonProperty("use_home_position_restriction") @Nullable Boolean useHomePositionRestriction
) {
}
