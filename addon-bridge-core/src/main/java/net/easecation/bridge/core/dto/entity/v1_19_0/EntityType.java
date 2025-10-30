package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The entity type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EntityType(
    @JsonProperty("filters") @Nullable Filters filters,
    /* Maximum distance this mob can be away to be a valid choice. */
    @JsonProperty("max_dist") @Nullable Double maxDist,
    /* Multiplier for the walking speed. A value of 1.0 means the speed is unchanged */
    @JsonProperty("walk_speed_multiplier") @Nullable Double walkSpeedMultiplier,
    /* Multiplier for the running speed. A value of 1.0 means the speed is unchanged */
    @JsonProperty("sprint_speed_multiplier") @Nullable Double sprintSpeedMultiplier,
    /* If true, the mob has to be visible to be a valid choice. */
    @JsonProperty("must_see") @Nullable Boolean mustSee,
    /* Determines the amount of time in seconds that this mob will look for a target before forgetting about it and looking for a new one when the target isn't visible any more. */
    @JsonProperty("must_see_forget_duration") @Nullable Double mustSeeForgetDuration
) {
}
