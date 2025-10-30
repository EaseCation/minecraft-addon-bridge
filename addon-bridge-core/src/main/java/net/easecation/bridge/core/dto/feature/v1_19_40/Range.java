package net.easecation.bridge.core.dto.feature.v1_19_40;

import com.fasterxml.jackson.annotation.*;

/* A range. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Range(
    /* The maximum plant height. */
    @JsonProperty("range_max") Integer rangeMax,
    /* The minimum plant height. */
    @JsonProperty("range_min") Integer rangeMin
) {
}
