package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* [EXPERIMENTAL BEHAVIOR] Plays the provided sound and activates the {@code ROARING} actor flag during the specified duration */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Roar(
    @JsonProperty("priority") @Nullable Integer priority,
    /* Goal duration in seconds. */
    @JsonProperty("duration") @Nullable Double duration
) {
}
