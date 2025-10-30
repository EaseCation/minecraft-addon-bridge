package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to eat/raid crops out of farms until they are full. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RaidGarden(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Blocks that the mob is looking for to eat. */
    @JsonProperty("blocks") @Nullable List<BlockReference> blocks,
    /* Time in seconds between each time it eats. */
    @JsonProperty("eat_delay") @Nullable Integer eatDelay,
    /* Amount of time in seconds before this mob wants to eat again. */
    @JsonProperty("full_delay") @Nullable Integer fullDelay,
    /* Time in seconds before starting to eat/raid once it arrives at it. */
    @JsonProperty("initial_eat_delay") @Nullable Integer initialEatDelay,
    /* Distance in blocks within the mob considers it has reached the goal. This is the {@code wiggle room} to stop the AI from bouncing back and forth trying to reach a specific spot */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* Maximum number of things this entity wants to eat. */
    @JsonProperty("max_to_eat") @Nullable Integer maxToEat,
    /* Distance in blocks the mob will look for crops to eat. */
    @JsonProperty("search_range") @Nullable Integer searchRange,
    /* Height in blocks the mob will look for crops to eat. */
    @JsonProperty("search_height") @Nullable Integer searchHeight
) {
}
