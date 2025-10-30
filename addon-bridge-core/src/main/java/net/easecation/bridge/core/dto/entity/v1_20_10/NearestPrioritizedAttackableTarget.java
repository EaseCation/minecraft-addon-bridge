package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to check for and pursue the nearest valid target. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record NearestPrioritizedAttackableTarget(
    @JsonProperty("priority") @Nullable Integer priority,
    /* List of entity types that this mob considers valid targets */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes,
    /* Time in seconds before selecting a target. */
    @JsonProperty("attack_interval") @Nullable Integer attackInterval,
    /* If true, only entities that this mob can path to can be selected as targets. */
    @JsonProperty("must_reach") @Nullable Boolean mustReach,
    /* If true, only entities in this mob's viewing range can be selected as targets. */
    @JsonProperty("must_see") @Nullable Boolean mustSee,
    /* Determines the amount of time in seconds that this mob will look for a target before forgetting about it and looking for a new one when the target isn't visible any more. */
    @JsonProperty("must_see_forget_duration") @Nullable Double mustSeeForgetDuration,
    /* Time in seconds for a valid target to stay targeted when it becomes and invalid target. */
    @JsonProperty("persist_time") @Nullable Double persistTime,
    /* If true, the target will change to the current closest entity whenever a different entity is closer. */
    @JsonProperty("reselect_targets") @Nullable Boolean reselectTargets,
    /* If true, the mob will stop being targeted if it stops meeting any conditions. */
    @JsonProperty("reevaluate_description") @Nullable Boolean reevaluateDescription,
    /* How many ticks to wait between scanning for a target. */
    @JsonProperty("scan_interval") @Nullable Integer scanInterval,
    /* Allows the actor to be set to persist upon targeting a player. */
    @JsonProperty("set_persistent") @Nullable Boolean setPersistent,
    /* Height in blocks to search for a target mob. -1.0f means the height does not matter. */
    @JsonProperty("target_search_height") @Nullable Double targetSearchHeight,
    /* Distance in blocks that the target can be within to launch an attack. */
    @JsonProperty("within_radius") @Nullable Double withinRadius
) {
}
