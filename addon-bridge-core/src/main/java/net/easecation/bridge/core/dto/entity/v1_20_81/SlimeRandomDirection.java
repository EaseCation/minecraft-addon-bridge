package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Can only be used by Slimes and Magma Cubes. Allows the mob to move in random directions like a slime. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SlimeRandomDirection(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Additional time (in whole seconds), chosen randomly in the range of [0, "add<i>random</i>time<i>range"], to add to "min</i>change<i>direction</i>time". */
    @JsonProperty("add_random_time_range") @Nullable Integer addRandomTimeRange,
    /* Constant minimum time (in seconds) to wait before choosing a new direction. */
    @JsonProperty("min_change_direction_time") @Nullable Double minChangeDirectionTime,
    /* Maximum rotation angle range (in degrees) when randomly choosing a new direction. */
    @JsonProperty("turn_range") @Nullable Integer turnRange
) {
}
