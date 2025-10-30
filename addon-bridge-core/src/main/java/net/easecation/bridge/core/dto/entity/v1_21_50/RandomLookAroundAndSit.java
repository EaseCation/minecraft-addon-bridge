package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to randomly sit and look around for a duration. Note: Must have a sitting animation set up to use this. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomLookAroundAndSit(
    @JsonProperty("priority") @Nullable Integer priority,
    /* If the goal should continue to be used as long as the mob is leashed. */
    @JsonProperty("continue_if_leashed") @Nullable Boolean continueIfLeashed,
    /* The mob will stay sitting on reload. */
    @JsonProperty("continue_sitting_on_reload") @Nullable Boolean continueSittingOnReload,
    /* The rightmost angle a mob can look at on the horizontal plane with respect to its initial facing direction. */
    @JsonProperty("max_angle_of_view_horizontal") @Nullable Double maxAngleOfViewHorizontal,
    /* The max amount of unique looks a mob will have while looking around. */
    @JsonProperty("max_look_count") @Nullable Integer maxLookCount,
    /* The max amount of time (in ticks) a mob will stay looking at a direction while looking around. */
    @JsonProperty("max_look_time") @Nullable Integer maxLookTime,
    /* The leftmost angle a mob can look at on the horizontal plane with respect to its initial facing direction. */
    @JsonProperty("min_angle_of_view_horizontal") @Nullable Double minAngleOfViewHorizontal,
    /* The min amount of unique looks a mob will have while looking around. */
    @JsonProperty("min_look_count") @Nullable Integer minLookCount,
    /* The min amount of time (in ticks) a mob will stay looking at a direction while looking around. */
    @JsonProperty("min_look_time") @Nullable Integer minLookTime,
    /* The probability of randomly looking around/sitting. */
    @JsonProperty("probability") @Nullable Double probability,
    /* The cooldown in seconds before the goal can be used again. */
    @JsonProperty("random_look_around_cooldown") @Nullable Integer randomLookAroundCooldown
) {
}
