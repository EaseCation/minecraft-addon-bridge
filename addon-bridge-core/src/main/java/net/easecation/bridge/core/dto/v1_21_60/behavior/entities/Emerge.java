package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Activates the {@code EMERGING} actor flag during the specified duration and triggers {@code on_done} at the end */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Emerge(
    @JsonProperty("priority") @Nullable Priority priority,
    /* Time in seconds the mob has to wait before using the goal again. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime,
    /* Goal duration in seconds. */
    @JsonProperty("duration") @Nullable Double duration,
    /* Trigger to be executed when the goal execution is about to end. */
    @JsonProperty("on_done") @Nullable Trigger onDone
) {
}
