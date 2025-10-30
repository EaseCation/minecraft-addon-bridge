package net.easecation.bridge.core.dto.entity.v1_21_50;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the entity's {@code peek} behavior, defining the events that should be called during it. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Peek(
    /* Event to call when the entity is done peeking. */
    @JsonProperty("on_close") @Nullable Event onClose,
    /* Event to call when the entity starts peeking. */
    @JsonProperty("on_open") @Nullable Event onOpen,
    /* Event to call when the entity's target entity starts peeking. */
    @JsonProperty("on_target_open") @Nullable Event onTargetOpen
) {
}
