package net.easecation.bridge.core.dto.block.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* [Experimental] Triggers the specified event randomly based on the random tick speed gamerule. The random tick speed determines how often blocks are updated. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RandomTicking(
    /* the event that will be triggered on random ticks. */
    @JsonProperty("on_tick") @Nullable Object onTick,
    /* A condition using Molang queries that results to true/false. If true on the random tick, the event will be triggered. If false on the random tick, the event will not be triggered. */
    @JsonProperty("condition") @Nullable String condition,
    /* The event that will be triggered. */
    @JsonProperty("event") @Nullable String event,
    /* The target of the event executed by the block */
    @JsonProperty("target") @Nullable String target
) {
}
