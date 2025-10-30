package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Defines the behavior when another entity looks at this entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LookedAt(
    /* [Beta] Defines, in degrees, the width of the field of view for entities looking at the owner entity. If 'scale<i>fov</i>by_distance' is set to true, this value corresponds to the field of view at a distance of one block between the entities. */
    @JsonProperty("field_of_view") @Nullable Double fieldOfView,
    /* Defines the entities that can trigger this component. */
    @JsonProperty("filters") @Nullable Filters filters,
    /* [Beta] Limits the search to only the nearest Player that meets the specified "filters" rather than all nearby entities. */
    @JsonProperty("find_players_only") @Nullable Boolean findPlayersOnly,
    /* [Beta] Defines the type of block shape used to check for line of sight obstructions. */
    @JsonProperty("line_of_sight_obstruction_type") @Nullable String lineOfSightObstructionType,
    /* [Beta] A list of locations on the owner entity towards which line of sight checks are performed. At least one location must be unobstructed for the entity to be considered as looked at. */
    @JsonProperty("look_at_locations") @Nullable List<String> lookAtLocations,
    /* The range for the random amount of time during which the entity is {@code cooling down} and won't get angered or look for a target. */
    @JsonProperty("looked_at_cooldown") @Nullable Range_a_B lookedAtCooldown,
    /* The event identifier to run when the entities specified in filters look at this entity. */
    @JsonProperty("looked_at_event") @Nullable Event lookedAtEvent,
    /* [Beta] Defines the event to trigger when no entity is found looking at the owner entity. */
    @JsonProperty("not_looked_at_event") @Nullable Event notLookedAtEvent,
    /* [Beta] When true, the field of view narrows as the distance between the owner entity and the entity looking at it increases. This ensures that the width of the view cone remains somewhat constant towards the owner entity position, regardless of distance. */
    @JsonProperty("scale_fov_by_distance") @Nullable Boolean scaleFovByDistance,
    /* Maximum distance this entity will look for another entity looking at it. */
    @JsonProperty("search_radius") @Nullable Double searchRadius,
    /*
 * Defines if and how the owner entity will set entities that are looking at it as its combat targets. Valid values:
 * - "never", looking entities are never set as targets, but events are emitted.
 * - "once<i>and</i>stop<i>scanning", the first detected looking entity is set as target. Scanning and event emission is suspended if and until the owner entity has a target.
 * - [Beta] "once</i>and<i>keep</i>scanning", the first detected looking entity is set as target. Scanning and event emission continues.
 */
    @JsonProperty("set_target") @Nullable String setTarget
) {
}
