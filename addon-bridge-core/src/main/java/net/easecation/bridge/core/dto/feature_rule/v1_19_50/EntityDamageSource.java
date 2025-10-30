package net.easecation.bridge.core.dto.feature_rule.v1_19_50;

import com.fasterxml.jackson.annotation.*;

/* The types of damage an entity can receive. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EntityDamageSource(
    /* The types of damage an entity can receive. */
    String value
) {
}
