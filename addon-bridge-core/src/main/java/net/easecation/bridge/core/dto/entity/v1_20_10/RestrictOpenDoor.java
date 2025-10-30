package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to stay indoors during night time. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RestrictOpenDoor(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
