package net.easecation.bridge.core.dto.block.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Describes event for this block. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnPlayerDestroyed(
    /* The condition of event to be executed on the block. */
    @JsonProperty("condition") @Nullable String condition,
    /* The event executed on the block. */
    @JsonProperty("event") @Nullable String event,
    /* The target of event executed on the block. */
    @JsonProperty("target") @Nullable String target
) {
}
