package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Keeps track of entity group size in the given radius. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GroupSize(
    /* The list of conditions that must be satisfied for other entities to be counted towards group size. */
    @JsonProperty("filters") @Nullable Filters filters,
    /* Radius from center of entity. */
    @JsonProperty("radius") @Nullable Double radius
) {
}
