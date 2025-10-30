package net.easecation.bridge.core.dto.animation_controller.v1_19_40;

import com.fasterxml.jackson.annotation.*;

/* The event or commands to execute. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Commands {
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Commands_Event(
    ) implements Commands {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Commands_Command(
    ) implements Commands {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Commands_Molang(
    ) implements Commands {
    }
}
