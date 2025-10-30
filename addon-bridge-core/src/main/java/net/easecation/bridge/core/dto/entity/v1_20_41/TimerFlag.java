package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Fires an event when this behavior starts, then waits for a duration before stopping. When stopping due to that timeout or due to being interrupted by another behavior, fires another event. query.timer<i>flag</i><n> will return 1.0 on both the client and server when this behavior is running, and 0.0 otherwise. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TimerFlag(
    /* Goal cooldown range in seconds. */
    @JsonProperty("cooldown_range") @Nullable VectorOf2Items cooldownRange,
    /* Goal duration range in seconds. */
    @JsonProperty("duration_range") @Nullable VectorOf2Items durationRange,
    /* Event to run when the goal ends. */
    @JsonProperty("on_end") @Nullable Trigger onEnd,
    /* Event to run when the goal starts. */
    @JsonProperty("on_start") @Nullable Trigger onStart
) {
}
