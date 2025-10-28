package net.easecation.bridge.core.dto.v1_21_60.behavior.cameras.presets;

import com.fasterxml.jackson.annotation.*;

/* A minecraft camera identifier. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CameraIdentifier(
    /* A minecraft camera identifier. */
    String value
) {
}
