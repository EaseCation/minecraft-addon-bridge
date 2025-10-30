package net.easecation.bridge.core.dto.spawn_rule.v1_20_10;

import com.fasterxml.jackson.annotation.*;

/* The subject of this filter test. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Subject(
    /* The subject of this filter test. */
    String value
) {
}
