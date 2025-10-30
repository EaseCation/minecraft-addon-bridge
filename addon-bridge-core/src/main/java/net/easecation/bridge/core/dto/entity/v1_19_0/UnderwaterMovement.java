package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UnderwaterMovement(
    /* UNDOCUMENTED. */
    @JsonProperty("value") @Nullable Double value
) {
}
