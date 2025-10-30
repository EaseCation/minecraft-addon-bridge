package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;

/* An vector of 3 number. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record VectorOf3Items(
) {
}
