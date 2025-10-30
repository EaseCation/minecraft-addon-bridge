package net.easecation.bridge.core.dto.spawn_rule.v1_20_10;

import com.fasterxml.jackson.annotation.*;

/* The comparison to apply with {@code value}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Operator(
    /* The comparison to apply with {@code value}. */
    String value
) {
}
