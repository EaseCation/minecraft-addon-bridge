package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Saves a home pos for when the the entity is spawned. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Home(
    /* The radius that the entity will be restricted to in relation to its home. */
    @JsonProperty("restriction_radius") @Nullable Integer restrictionRadius,
    /* Optional block list that the home position will be associated with. If any of the blocks no longer exist at that position, the home restriction is removed. Example syntax: minecraft:sand. Not supported: minecraft:sand:1 */
    @JsonProperty("home_block_list") @Nullable List<ItemIdentifier> homeBlockList,
    /*
 * Defines how the the entity will be restricted to its home position. The possible values are:
 * - 'none', which poses no restriction.
 * - 'random<i>movement', which restricts randomized movement to be around the home position.
 * - 'all</i>movement', which restricts any kind of movement to be around the home position. However, entities that somehow got too far away from their home will always be able to move closer to it, if prompted to do so.
 */
    @JsonProperty("restriction_type") @Nullable String restrictionType
) {
}
