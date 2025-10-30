package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Despawns the Actor when the despawn rules or optional filters evaluate to true. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Despawn(
    /* Determines if {@code min<i>range</i>random_chance} is used in the standard despawn rules. */
    @JsonProperty("despawn_from_chance") @Nullable Boolean despawnFromChance,
    /* Defines the minimum and maximum distance for despawn to occur. */
    @JsonProperty("despawn_from_distance") @Nullable DespawnFromDistance despawnFromDistance,
    /* Determines if the {@code min<i>range</i>inactivity_timer} is used in the standard despawn rules. */
    @JsonProperty("despawn_from_inactivity") @Nullable Boolean despawnFromInactivity,
    /* Determines if the mob is instantly despawned at the edge of simulation distance in the standard despawn rules. */
    @JsonProperty("despawn_from_simulation_edge") @Nullable Boolean despawnFromSimulationEdge,
    /* The list of conditions that must be satisfied before the Actor is despawned. If a filter is defined then standard despawn rules are ignored. */
    @JsonProperty("filters") @Nullable Filters filters,
    /* The amount of time in seconds that the mob must be inactive. */
    @JsonProperty("min_range_inactivity_timer") @Nullable Integer minRangeInactivityTimer,
    /* A random chance between 1 and the given value. */
    @JsonProperty("min_range_random_chance") @Nullable Integer minRangeRandomChance,
    /* If true, all entities linked to this entity in a child relationship (eg. leashed) will also be despawned. */
    @JsonProperty("remove_child_entities") @Nullable Boolean removeChildEntities
) {
    
        /* Defines the minimum and maximum distance for despawn to occur. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record DespawnFromDistance(
            /* Maximum distance for standard despawn rules to instantly despawn the mob. */
            @JsonProperty("max_distance") @Nullable Integer maxDistance,
            /* Minimum distance for standard despawn rules to try to despawn the mob. */
            @JsonProperty("min_distance") @Nullable Integer minDistance
        ) {
        }
}
