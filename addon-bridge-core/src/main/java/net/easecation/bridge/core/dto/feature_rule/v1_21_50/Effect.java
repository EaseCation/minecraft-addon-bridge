package net.easecation.bridge.core.dto.feature_rule.v1_21_50;

import com.fasterxml.jackson.annotation.*;

/* A mob effect */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Effect(
    /* A mob effect */
    String value
) {
}
