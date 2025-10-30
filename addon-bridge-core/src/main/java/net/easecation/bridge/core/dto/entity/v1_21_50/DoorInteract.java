package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the mob to open and close doors. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DoorInteract(
    @JsonProperty("priority") @Nullable Integer priority
) {
}
