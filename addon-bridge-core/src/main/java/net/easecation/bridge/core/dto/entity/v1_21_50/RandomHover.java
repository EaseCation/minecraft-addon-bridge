package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to hover around randomly, close to the surface. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomHover(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* The height above the surface which the mob will try to maintain. */
    @JsonProperty("hover_height") @Nullable List<Double> hoverHeight,
    /* A random value to determine when to randomly move somewhere. This has a 1/interval chance to choose this goal */
    @JsonProperty("interval") @Nullable Integer interval,
    /* Distance in blocks on ground that the mob will look for a new spot to move to. Must be at least 1 */
    @JsonProperty("xz_dist") @Nullable Integer xzDist,
    /* Distance in blocks that the mob will look up or down for a new spot to move to. Must be at least 1 */
    @JsonProperty("y_dist") @Nullable Integer yDist,
    /* Height in blocks to add to the selected target position. */
    @JsonProperty("y_offset") @Nullable Double yOffset
) {
}
