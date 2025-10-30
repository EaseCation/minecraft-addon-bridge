package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows an entity to randomly move through water. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomSwim(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* If true, the mob will avoid surface water blocks by swimming below them. */
    @JsonProperty("avoid_surface") @Nullable Boolean avoidSurface,
    /* A random value to determine when to randomly move somewhere. This has a 1/interval chance to choose this goal */
    @JsonProperty("interval") @Nullable Integer interval,
    /* Distance in blocks on ground that the mob will look for a new spot to move to. Must be at least 1 */
    @JsonProperty("xz_dist") @Nullable Integer xzDist,
    /* Distance in blocks that the mob will look up or down for a new spot to move to. Must be at least 1 */
    @JsonProperty("y_dist") @Nullable Integer yDist
) {
}
