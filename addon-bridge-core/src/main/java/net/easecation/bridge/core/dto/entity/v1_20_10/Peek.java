package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to peek out. This is what the shulker uses to look out of its shell. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Peek(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
