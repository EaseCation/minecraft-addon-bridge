package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;

/* The subject of this filter test. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Subject(
    /* The subject of this filter test. */
    String value
) {
}
