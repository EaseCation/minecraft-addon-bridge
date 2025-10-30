package net.easecation.bridge.core.dto.block.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* [Experimental] Describes event for this block. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record OnFallOn(
    /* The condition of event to be executed on the block. */
    @JsonProperty("condition") @Nullable String condition,
    /* The event executed on the block. */
    @JsonProperty("event") @Nullable String event,
    /* The target of event executed on the block. */
    @JsonProperty("target") @Nullable String target
) {
}
