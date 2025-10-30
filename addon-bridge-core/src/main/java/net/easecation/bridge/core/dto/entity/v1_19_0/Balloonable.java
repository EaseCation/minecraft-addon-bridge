package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Balloonable(
    /* UNDOCUMENTED: mass. */
    @JsonProperty("mass") @Nullable Double mass
) {
}
