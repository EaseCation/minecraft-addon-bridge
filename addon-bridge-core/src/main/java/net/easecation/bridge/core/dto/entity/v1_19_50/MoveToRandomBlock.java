package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows mob to move towards a random block. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MoveToRandomBlock(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Defines the distance from the mob, in blocks, that the block to move to will be chosen. */
    @JsonProperty("block_distance") @Nullable Double blockDistance,
    /* Defines the distance in blocks the mob has to be from the block for the movement to be finished. */
    @JsonProperty("within_radius") @Nullable Double withinRadius
) {
}
