package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the entity to first travel to a random point on the outskirts of the village, and then explore random points within a small distance. This goal requires "minecraft:dweller" and "minecraft:navigation" to execute. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ExploreOutskirts(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* The distance from the boundary the villager must be within in to explore the outskirts. */
    @JsonProperty("dist_from_boundary") @Nullable VectorOf3Items distFromBoundary,
    /* Total distance in blocks the the entity will explore beyond the village bounds when choosing its travel point. */
    @JsonProperty("explore_dist") @Nullable Double exploreDist,
    /* This is the maximum amount of time an entity will attempt to reach it's travel point on the outskirts of the village before the goal exits. */
    @JsonProperty("max_travel_time") @Nullable Double maxTravelTime,
    /* The wait time in seconds between choosing new explore points will be chosen on a random interval between this value and the minimum wait time. This value is also the total amount of time the entity will explore random points before the goal stops. */
    @JsonProperty("max_wait_time") @Nullable Double maxWaitTime,
    /* The entity must be within this distance for it to consider it has successfully reached its target. */
    @JsonProperty("min_dist_from_target") @Nullable Double minDistFromTarget,
    /* The minimum perimeter of the village required to run this goal. */
    @JsonProperty("min_perimeter") @Nullable Double minPerimeter,
    /* The wait time in seconds between choosing new explore points will be chosen on a random interval between this value and the maximum wait time. */
    @JsonProperty("min_wait_time") @Nullable Double minWaitTime,
    /* A new explore point will randomly be chosen within this XZ distance of the current target position when navigation has finished and the wait timer has elapsed. */
    @JsonProperty("next_xz") @Nullable Integer nextXz,
    /* A new explore point will randomly be chosen within this Y distance of the current target position when navigation has finished and the wait timer has elapsed. */
    @JsonProperty("next_y") @Nullable Integer nextY,
    /* Each new explore point will be chosen on a random interval between the minimum and the maximum wait time, divided by this value. This does not apply to the first explore point chosen when the goal runs. */
    @JsonProperty("timer_ratio") @Nullable Double timerRatio
) {
}
