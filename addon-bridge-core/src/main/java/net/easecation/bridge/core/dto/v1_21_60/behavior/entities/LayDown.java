package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows mobs to lay down at times. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LayDown(
    @JsonProperty("priority") @Nullable Integer priority,
    /* A random value to determine at what intervals something can occur. This has a 1/interval chance to choose this goal */
    @JsonProperty("interval") @Nullable Integer interval,
    /* A random value in which the goal can use to pull out of the behavior. This is a 1/interval chance to play the sound */
    @JsonProperty("random_stop_interval") @Nullable Integer randomStopInterval
) {
}
