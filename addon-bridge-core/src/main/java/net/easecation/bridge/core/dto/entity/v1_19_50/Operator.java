package net.easecation.bridge.core.dto.entity.v1_19_50;

import com.fasterxml.jackson.annotation.*;

/* The comparison to apply with {@code value}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Operator(
    /* The comparison to apply with {@code value}. */
    String value
) {
}
