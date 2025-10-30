package net.easecation.bridge.core.dto.feature_rule.v1_19_50;

import com.fasterxml.jackson.annotation.*;

/* The subject of this filter test. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Subject(
    /* The subject of this filter test. */
    String value
) {
}
