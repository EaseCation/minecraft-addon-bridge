package net.easecation.bridge.core.dto.animation.v1_21_60;

import com.fasterxml.jackson.annotation.*;

/* The event or commands to execute. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface Commands {
    /* Sets the value to a molang variable. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Commands_Variable(
    ) implements Commands {
    }
    /* Executes a minecraft command. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Commands_MinecraftCommand(
    ) implements Commands {
    }
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Commands_Molang(
    ) implements Commands {
    }
    /* An event to be called upon within the executing entity. */
    @JsonIgnoreProperties(ignoreUnknown = true) 
    record Commands_Event(
    ) implements Commands {
    }
}
