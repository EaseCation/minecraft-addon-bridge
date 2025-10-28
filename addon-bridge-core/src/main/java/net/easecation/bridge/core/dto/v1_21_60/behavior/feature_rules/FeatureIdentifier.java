package net.easecation.bridge.core.dto.v1_21_60.behavior.feature_rules;

import com.fasterxml.jackson.annotation.*;

/* A minecraft feature identifier. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record FeatureIdentifier(
    /* A minecraft feature identifier. */
    String value
) {
}
