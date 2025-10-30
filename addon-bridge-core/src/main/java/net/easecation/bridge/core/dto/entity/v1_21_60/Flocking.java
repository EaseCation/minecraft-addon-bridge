package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows entities to flock in groups in water or not. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Flocking(
    /* The amount of blocks away the entity will look at to push away from. */
    @JsonProperty("block_distance") @Nullable Double blockDistance,
    /* The weight of the push back away from blocks. */
    @JsonProperty("block_weight") @Nullable Double blockWeight,
    /* The amount of push back given to a flocker that breaches out of the water. */
    @JsonProperty("breach_influence") @Nullable Double breachInfluence,
    /* The threshold in which to start applying cohesion. */
    @JsonProperty("cohesion_threshold") @Nullable Double cohesionThreshold,
    /* The weight applied for the cohesion steering of the flock. */
    @JsonProperty("cohesion_weight") @Nullable Double cohesionWeight,
    /* The weight on which to apply on the goal output. */
    @JsonProperty("goal_weight") @Nullable Double goalWeight,
    /* Determines the high bound amount of entities that can be allowed in the flock. */
    @JsonProperty("high_flock_limit") @Nullable Integer highFlockLimit,
    /* Tells the Flocking Component if the entity exists in water. */
    @JsonProperty("in_water") @Nullable Boolean inWater,
    /* The area around the entity that allows others to be added to the flock. */
    @JsonProperty("influence_radius") @Nullable Double influenceRadius,
    /* The distance in which the flocker will stop applying cohesion. */
    @JsonProperty("innner_cohesion_threshold") @Nullable Double innnerCohesionThreshold,
    /* The percentage chance between 0-1 that a fish will spawn and not want to join flocks. Invalid values will be capped at the end points. */
    @JsonProperty("loner_chance") @Nullable Double lonerChance,
    /* Determines the low bound amount of entities that can be allowed in the flock. */
    @JsonProperty("low_flock_limit") @Nullable Integer lowFlockLimit,
    /* Tells the flockers that they can only match similar entities that also match the variant, mark variants, and color data of the other potential flockers. */
    @JsonProperty("match_variants") @Nullable Boolean matchVariants,
    /* The Maximum height allowable in the air or water. */
    @JsonProperty("max_height") @Nullable Double maxHeight,
    /* The Minimum height allowable in the air or water. */
    @JsonProperty("min_height") @Nullable Double minHeight,
    /* The distance that is determined to be to close to another flocking and to start applying separation. */
    @JsonProperty("separation_threshold") @Nullable Double separationThreshold,
    /* The weight applied to the separation of the flock. */
    @JsonProperty("separation_weight") @Nullable Double separationWeight,
    /* Tells the flockers that they will follow flocks based on the center of mass. */
    @JsonProperty("use_center_of_mass") @Nullable Boolean useCenterOfMass
) {
}
