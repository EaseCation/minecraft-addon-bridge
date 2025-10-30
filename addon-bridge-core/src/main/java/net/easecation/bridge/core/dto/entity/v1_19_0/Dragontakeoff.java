package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the dragon to leave perch mode and go back to flying around. Can only be used by the Ender Dragon. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dragontakeoff(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
