package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* The entity type. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EntityType(
    @JsonProperty("filters") @Nullable Filters filters,
    /* Maximum distance this mob can be away to be a valid choice. */
    @JsonProperty("max_dist") @Nullable Double maxDist,
    /* UNDOCUMENTED. */
    @JsonProperty("max_height") @Nullable Double maxHeight,
    /* UNDOCUMENTED. */
    @JsonProperty("max_flee") @Nullable Double maxFlee,
    /* UNDOCUMENTED. */
    @JsonProperty("priority") @Nullable Double priority,
    /* UNDOCUMENTED. */
    @JsonProperty("check_if_outnumbered") @Nullable Double checkIfOutnumbered,
    /* If true, the mob has to be visible to be a valid choice. */
    @JsonProperty("must_see") @Nullable Boolean mustSee,
    /* Determines the amount of time in seconds that this mob will look for a target before forgetting about it and looking for a new one when the target isn't visible any more. */
    @JsonProperty("must_see_forget_duration") @Nullable Double mustSeeForgetDuration,
    /* If true, the mob will stop being targeted if it stops meeting any conditions. */
    @JsonProperty("reevaluate_description") @Nullable Boolean reevaluateDescription,
    /* Multiplier for the running speed. A value of 1.0 means the speed is unchanged */
    @JsonProperty("sprint_speed_multiplier") @Nullable Double sprintSpeedMultiplier,
    /* Multiplier for the walking speed. A value of 1.0 means the speed is unchanged */
    @JsonProperty("walk_speed_multiplier") @Nullable Double walkSpeedMultiplier
) {
}
