package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to perform a damaging knockback that affects all nearby entities. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record KnockbackRoar(
    @JsonProperty("priority") @Nullable Integer priority,
    /* The delay after which the knockback occurs (in seconds). */
    @JsonProperty("attack_time") @Nullable Double attackTime,
    /* Time in seconds the mob has to wait before using the goal again. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime,
    /* The list of conditions another entity must meet to be a valid target to apply damage to. */
    @JsonProperty("damage_filters") @Nullable Filters damageFilters,
    /* The duration of the roar (in seconds). */
    @JsonProperty("duration") @Nullable Double duration,
    /* The damage dealt by the knockback roar. */
    @JsonProperty("knockback_damage") @Nullable Integer knockbackDamage,
    /* The strength of the knockback. */
    @JsonProperty("knockback_strength") @Nullable Integer knockbackStrength,
    /* The list of conditions another entity must meet to be a valid target to apply knockback to. */
    @JsonProperty("knockback_filters") @Nullable Filters knockbackFilters,
    /* The strength of the horizontal knockback. */
    @JsonProperty("knockback_horizontal_strength") @Nullable Integer knockbackHorizontalStrength,
    /* The radius (in blocks) of the knockback effect. */
    @JsonProperty("knockback_range") @Nullable Integer knockbackRange,
    /* The strength of the vertical knockback. */
    @JsonProperty("knockback_vertical_strength") @Nullable Integer knockbackVerticalStrength,
    /* The maximum height for vertical knockback. */
    @JsonProperty("knockback_height_cap") @Nullable Double knockbackHeightCap,
    /* If true, this mob will chase after the target as long as it's a valid target. */
    @JsonProperty("track_target") @Nullable Boolean trackTarget,
    /* Event that is triggered when the roar ends. */
    @JsonProperty("on_roar_end") @Nullable Event onRoarEnd
) {
}
