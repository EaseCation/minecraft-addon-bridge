package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sets that this entity can be stacked. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsStackable(
    /* UNDOCUMENTED. */
    @JsonProperty("value") @Nullable Boolean value
) {
}
