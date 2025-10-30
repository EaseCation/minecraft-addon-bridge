package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;

/* The types of damage an entity can receive. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EntityDamageSource(
    /* The types of damage an entity can receive. */
    String value
) {
}
