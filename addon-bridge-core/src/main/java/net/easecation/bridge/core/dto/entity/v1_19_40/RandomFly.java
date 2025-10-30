package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows a mob to randomly fly around. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomFly(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* If true, the mob will avoid blocks that cause damage. */
    @JsonProperty("avoid_damage_blocks") @Nullable Boolean avoidDamageBlocks,
    /* If true, the mob can stop flying and land on a tree instead of the ground. */
    @JsonProperty("can_land_on_trees") @Nullable Boolean canLandOnTrees,
    /* Distance in blocks on ground that the mob will look for a new spot to move to. Must be at least 1 */
    @JsonProperty("xz_dist") @Nullable Integer xzDist,
    /* Distance in blocks that the mob will look up or down for a new spot to move to. Must be at least 1 */
    @JsonProperty("y_dist") @Nullable Integer yDist,
    /* Height in blocks to add to the selected target position. */
    @JsonProperty("y_offset") @Nullable Integer yOffset
) {
}
