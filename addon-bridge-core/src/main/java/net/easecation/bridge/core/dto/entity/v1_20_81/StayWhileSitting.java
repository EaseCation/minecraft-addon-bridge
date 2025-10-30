package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to stay put while it is in a sitting state instead of doing something else. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StayWhileSitting(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
