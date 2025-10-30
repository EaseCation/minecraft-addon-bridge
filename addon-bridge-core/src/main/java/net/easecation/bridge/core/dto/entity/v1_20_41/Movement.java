package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Movement(
    /* UNDOCUMENTED. */
    @JsonProperty("value") @Nullable Object value,
    /* UNDOCUMENTED. */
    @JsonProperty("max") @Nullable Double max
) {
}
