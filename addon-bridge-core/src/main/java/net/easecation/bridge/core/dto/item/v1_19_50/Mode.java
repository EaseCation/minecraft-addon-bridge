package net.easecation.bridge.core.dto.item.v1_19_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Mode(
    /* The position transformation. */
    @JsonProperty("position") @Nullable Vector position,
    /* The rotation transformation. */
    @JsonProperty("rotation") @Nullable Vector rotation,
    /* The scale transformation. */
    @JsonProperty("scale") @Nullable Vector scale
) {
}
