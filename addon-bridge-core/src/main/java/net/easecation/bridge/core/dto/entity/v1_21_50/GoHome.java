package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Allows the mob to move back to the position they were spawned. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GoHome(
    @JsonProperty("priority") @Nullable Integer priority,
    @JsonProperty("speed_multiplier") @Nullable Double speedMultiplier,
    /* Distance in blocks within the mob considers it has reached the goal. This is the {@code wiggle room} to stop the AI from bouncing back and forth trying to reach a specific spot */
    @JsonProperty("goal_radius") @Nullable Double goalRadius,
    /* A random value to determine when to randomly move somewhere. This has a 1/interval chance to choose this goal */
    @JsonProperty("interval") @Nullable Integer interval,
    /* Event(s) to run when this mob gets home. */
    @JsonProperty("on_home") @Nullable Trigger onHome,
    /* Event(s) to run when this goal fails. */
    @JsonProperty("on_failed") @Nullable List<Event> onFailed,
    /* Distance in blocks that the mob is considered close enough to the end of the current path. A new path will then be calculated to continue toward home. */
    @JsonProperty("calculate_new_path_radius") @Nullable Double calculateNewPathRadius
) {
}
