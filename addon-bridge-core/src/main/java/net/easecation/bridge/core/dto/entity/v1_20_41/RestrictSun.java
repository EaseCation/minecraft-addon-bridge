package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to automatically start avoiding the sun when its a clear day out. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RestrictSun(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
