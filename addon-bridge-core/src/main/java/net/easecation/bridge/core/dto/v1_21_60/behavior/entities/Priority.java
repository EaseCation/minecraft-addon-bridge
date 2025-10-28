package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* How important this behavior is. Lower priority behaviors will be executed first. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Priority(
    /* How important this behavior is. Lower priority behaviors will be executed first. */
    Integer value
) {
}
