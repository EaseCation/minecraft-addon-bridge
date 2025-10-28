package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* Fires an event when this behavior starts, then waits for a duration before stopping. When stopping due to that timeout or due to being interrupted by another behavior, fires another event. query.timer<i>flag</i><n> will return 1.0 on both the client and server when this behavior is running, and 0.0 otherwise. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TimerFlag(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Goal cooldown range in seconds. */
    @JsonProperty("cooldown_range") @Nullable Object cooldownRange,
    /* Goal duration range in seconds. */
    @JsonProperty("duration_range") @Nullable Object durationRange,
    /* UNDOCUMENTED */
    @JsonProperty("control_flags") @Nullable List<String> controlFlags,
    /* Event to run when the goal ends. */
    @JsonProperty("on_end") @Nullable Trigger onEnd,
    /* Event to run when the goal starts. */
    @JsonProperty("on_start") @Nullable Trigger onStart
) {
}
