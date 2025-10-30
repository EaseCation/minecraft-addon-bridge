package net.easecation.bridge.core.dto.spawn_rule.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Herd. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Herd(
    /* UNDOCUMENTED. */
    @JsonProperty("initial_event") @Nullable String initialEvent,
    /* UNDOCUMENTED. */
    @JsonProperty("initial_event_count") @Nullable Integer initialEventCount,
    /* This is the minimum number of mobs that spawn in a herd. */
    @JsonProperty("min_size") @Nullable Integer minSize,
    /* This is the maximum number of mobs that spawn in a herd. */
    @JsonProperty("max_size") @Nullable Integer maxSize,
    /* This is an event that can be triggered from spawning. */
    @JsonProperty("event") @Nullable String event,
    /* This is the number of mobs spawned before the specified event is triggered. */
    @JsonProperty("event_skip_count") @Nullable Integer eventSkipCount
) {
}
