package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* [EXPERIMENTAL BEHAVIOR] Activates the {@code DIGGING} actor flag during the specified duration. Currently only Warden can use the Dig goal */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dig(
    @JsonProperty("priority") @Nullable Integer priority,
    /* If true, this behavior can run when this entity is named. Otherwise not. */
    @JsonProperty("allow_dig_when_named") @Nullable Boolean allowDigWhenNamed,
    /* Indicates that the actor should start digging when it sees daylight. */
    @JsonProperty("digs_in_daylight") @Nullable Boolean digsInDaylight,
    /* Goal duration in seconds. */
    @JsonProperty("duration") @Nullable Double duration,
    /* The minimum idle time in seconds between the last detected disturbance to the start of digging. */
    @JsonProperty("idle_time") @Nullable Double idleTime,
    /* If true, finding new suspicious locations count as disturbances that may delay the start of this goal. */
    @JsonProperty("suspicion_is_disturbance") @Nullable Boolean suspicionIsDisturbance,
    /* If true, vibrations count as disturbances that may delay the start of this goal. */
    @JsonProperty("vibration_is_disturbance") @Nullable Boolean vibrationIsDisturbance,
    /* The event to run when the goal start */
    @JsonProperty("on_start") @Nullable Trigger onStart
) {
}
