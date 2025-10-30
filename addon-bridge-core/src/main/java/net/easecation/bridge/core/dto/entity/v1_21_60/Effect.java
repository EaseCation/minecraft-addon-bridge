package net.easecation.bridge.core.dto.entity.v1_21_60;

import com.fasterxml.jackson.annotation.*;

/* A mob effect */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Effect(
    /* A mob effect */
    String value
) {
}
