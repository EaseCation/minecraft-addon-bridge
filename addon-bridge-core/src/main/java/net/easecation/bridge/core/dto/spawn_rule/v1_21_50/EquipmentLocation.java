package net.easecation.bridge.core.dto.spawn_rule.v1_21_50;

import com.fasterxml.jackson.annotation.*;

/* The equipment location to test. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EquipmentLocation(
    /* The equipment location to test. */
    String value
) {
}
