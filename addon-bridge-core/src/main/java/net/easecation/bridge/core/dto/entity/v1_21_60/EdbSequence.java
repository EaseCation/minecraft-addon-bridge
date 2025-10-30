package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;

/* List of events to send. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EdbSequence(
) {
}
