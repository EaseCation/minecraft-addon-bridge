package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows Equine mobs to be Horse Traps and be triggered like them, spawning a lightning bolt and a bunch of horses when a player is nearby. Can only be used by Horses, Mules, Donkeys and Skeleton Horses. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SkeletonHorseTrap(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Amount of time in seconds the trap exists. After this amount of time is elapsed, the trap is removed from the world if it hasn't been activated */
    @JsonProperty("duration") @Nullable Double duration,
    /* Distance in blocks that the player has to be within to trigger the horse trap. */
    @JsonProperty("within_radius") @Nullable Double withinRadius
) {
}
