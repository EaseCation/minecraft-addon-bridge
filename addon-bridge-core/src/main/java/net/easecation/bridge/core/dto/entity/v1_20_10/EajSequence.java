package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;

/* List of events to send. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EajSequence(
) {
}
