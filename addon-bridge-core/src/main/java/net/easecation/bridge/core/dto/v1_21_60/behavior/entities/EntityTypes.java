package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface EntityTypes {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntityTypes_Variant0(
        List<EntityType> value
    ) implements EntityTypes {
    }
    /* The entity type. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record EntityTypes_Variant1(
        @JsonProperty("filters") @Nullable Filters filters,
        /* The amount of time in seconds that the mob has to wait before selecting a target of the same type again */
        @JsonProperty("cooldown") @Nullable Double cooldown,
        /* Maximum distance this mob can be away to be a valid choice. */
        @JsonProperty("max_dist") @Nullable Double maxDist,
        /* UNDOCUMENTED. */
        @JsonProperty("max_height") @Nullable Double maxHeight,
        /* UNDOCUMENTED. */
        @JsonProperty("max_flee") @Nullable Double maxFlee,
        /* UNDOCUMENTED. */
        @JsonProperty("priority") @Nullable Double priority,
        /* UNDOCUMENTED. */
        @JsonProperty("within_default") @Nullable Double withinDefault,
        /* UNDOCUMENTED. */
        @JsonProperty("check_if_outnumbered") @Nullable Boolean checkIfOutnumbered,
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
    ) implements EntityTypes {
    }
}
