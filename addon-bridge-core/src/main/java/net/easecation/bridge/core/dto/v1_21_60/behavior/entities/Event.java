package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Minecraft behavior event. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Event {
    /* The event to fire. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Event_Variant0(
        /* The event to fire. */
        String value
    ) implements Event {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Event_Variant1(
        /* The event to fire. */
        @JsonProperty("event") @Nullable String event,
        /* The target of the event. */
        @JsonProperty("target") @Nullable String target
    ) implements Event {}
}
