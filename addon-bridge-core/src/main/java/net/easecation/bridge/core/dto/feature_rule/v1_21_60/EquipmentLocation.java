package net.easecation.bridge.core.dto.feature_rule.v1_21_60;

import com.fasterxml.jackson.annotation.*;

/* The equipment location to test. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EquipmentLocation(
    /* The equipment location to test. */
    String value
) {
}
