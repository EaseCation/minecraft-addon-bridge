package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Adds a timer for drying out that will count down and fire {@code dried<i>out</i>event} or will stop as soon as the entity will get under rain or water and fire {@code stopped<i>drying</i>out_event}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DryingOutTimer(
    /* Event to fire when the drying out time runs out. */
    @JsonProperty("dried_out_event") @Nullable Event driedOutEvent,
    /* Event to fire when entity was already dried out but received increase in water supply. */
    @JsonProperty("recover_after_dried_out_event") @Nullable Event recoverAfterDriedOutEvent,
    /* Event to fire when entity stopped drying out, for example got into water or under rain. */
    @JsonProperty("stopped_drying_out_event") @Nullable Event stoppedDryingOutEvent,
    /* Amount of time in seconds to dry out fully. */
    @JsonProperty("total_time") @Nullable Double totalTime,
    /* Optional amount of additional time in seconds given by using splash water bottle on entity. */
    @JsonProperty("water_bottle_refill_time") @Nullable Double waterBottleRefillTime
) {
}
