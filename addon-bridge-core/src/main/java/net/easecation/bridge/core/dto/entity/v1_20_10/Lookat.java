package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the behavior when another entity looks at this entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Lookat(
    /* If true, invulnerable entities (e.g. Players in creative mode) are considered valid targets. */
    @JsonProperty("allow_invulnerable") @Nullable Boolean allowInvulnerable,
    /* Defines the entities that can trigger this component. */
    @JsonProperty("filters") @Nullable Filters filters,
    /* The range for the random amount of time during which the entity is {@code cooling down} and won't get angered or look for a target. */
    @JsonProperty("look_cooldown") @Nullable Range_a_B lookCooldown,
    /* The event identifier to run when the entities specified in filters look at this entity. */
    @JsonProperty("look_event") @Nullable Event lookEvent,
    /* Maximum distance this entity will look for another entity looking at it. */
    @JsonProperty("search_radius") @Nullable Double searchRadius,
    /* If true, this entity will set the attack target as the entity that looked at it. */
    @JsonProperty("set_target") @Nullable Boolean setTarget
) {
}
