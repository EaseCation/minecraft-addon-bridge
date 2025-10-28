package net.easecation.bridge.core.dto.v1_21_60.behavior.animation_controllers;

import com.fasterxml.jackson.annotation.*;

/* The event or commands to execute. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Commands {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Commands_Event(
    ) implements Commands {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Commands_Command(
    ) implements Commands {}
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Commands_Molang(
    ) implements Commands {}
}
