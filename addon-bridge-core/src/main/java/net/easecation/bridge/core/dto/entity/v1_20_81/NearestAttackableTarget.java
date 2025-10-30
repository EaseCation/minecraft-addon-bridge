package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows an entity to attack the closest target within a given subset of specific target types. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record NearestAttackableTarget(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Filters which types of targets are valid for this entity */
    @JsonProperty("entity_types") @Nullable EntityTypes entityTypes,
    /* Time range (in seconds) between searching for an attack target, range is in (0, {@code attack<i>interval}]. Only used if {@code attack</i>interval} is greater than 0, otherwise {@code scan_interval} is used. */
    @JsonProperty("attack_interval") @Nullable Integer attackInterval,
    /* Alias for {@code attack<i>interval}; provides the same functionality as {@code attack</i>interval}. */
    @JsonProperty("attack_interval_min") @Nullable Double attackIntervalMin,
    /* If true, this entity can attack its owner. */
    @JsonProperty("attack_owner") @Nullable Boolean attackOwner,
    /* If true, this entity requires a path to the target. */
    @JsonProperty("must_reach") @Nullable Boolean mustReach,
    /* Determines if target-validity requires this entity to be in range only, or both in range and in sight. */
    @JsonProperty("must_see") @Nullable Boolean mustSee,
    /* Time (in seconds) the target must not be seen by this entity to become invalid. Used only if {@code must_see} is true. */
    @JsonProperty("must_see_forget_duration") @Nullable Double mustSeeForgetDuration,
    /* Time (in seconds) this entity can continue attacking the target after the target is no longer valid. */
    @JsonProperty("persist_time") @Nullable Double persistTime,
    /* Allows the attacking entity to update the nearest target, otherwise a target is only reselected after each {@code scan<i>interval} or {@code attack</i>interval}. */
    @JsonProperty("reselect_targets") @Nullable Boolean reselectTargets,
    /* If {@code attack<i>interval} is 0 or isn't declared, then between attacks: scanning for a new target occurs every amount of ticks equal to {@code scan</i>interval}, minimum value is 1. Values under 10 can affect performance. */
    @JsonProperty("scan_interval") @Nullable Integer scanInterval,
    /* Allows the actor to be set to persist upon targeting a player. */
    @JsonProperty("set_persistent") @Nullable Boolean setPersistent,
    /* Multiplied with the target's armor coverage percentage to modify {@code max_dist} when detecting an invisible target. */
    @JsonProperty("target_invisible_multiplier") @Nullable Double targetInvisibleMultiplier,
    /* Maximum vertical target-search distance, if it's greater than the target type's {@code max<i>dist}. A negative value defaults to {@code entity</i>types} greatest {@code max_dist}. */
    @JsonProperty("target_search_height") @Nullable Double targetSearchHeight,
    /* Multiplied with the target type's {@code max_dist} when trying to detect a sneaking target. */
    @JsonProperty("target_sneak_visibility_multiplier") @Nullable Double targetSneakVisibilityMultiplier,
    /* Maximum distance this entity can be from the target when following it, otherwise the target becomes invalid. This value is only used if the entity doesn't declare {@code minecraft:follow_range}. */
    @JsonProperty("within_radius") @Nullable Double withinRadius
) {
}
