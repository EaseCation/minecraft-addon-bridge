package net.easecation.bridge.core.dto.spawn_rule.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* entity_types is a specific type of JSON object used by Minecraft: Bedrock Edition in order to encapsulate entity data that can be used in certain behaviors and components.. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EntityTypes(
    /* Conditions that make this entry in the list valid. */
    @JsonProperty("filters") @Nullable Filters filters,
    /* Maximum distance this mob can be away to be a valid choice. */
    @JsonProperty("max_dist") @Nullable Double maxDist,
    /* If true, the mob has to be visible to be a valid choice. */
    @JsonProperty("must_see") @Nullable Boolean mustSee,
    /* Determines the amount of time in seconds that this mob will look for a target before forgetting about it and looking for a new one when the target isn't visible any more. */
    @JsonProperty("must_see_forget_duration") @Nullable Boolean mustSeeForgetDuration,
    /* Multiplier for the running speed. A value of 1.0 means the speed is unchanged. */
    @JsonProperty("sprint_speed_multiplier") @Nullable Double sprintSpeedMultiplier,
    /* Multiplier for the walking speed. A value of 1.0 means the speed is unchanged. */
    @JsonProperty("walk_speed_multiplier") @Nullable Double walkSpeedMultiplier
) {
}
