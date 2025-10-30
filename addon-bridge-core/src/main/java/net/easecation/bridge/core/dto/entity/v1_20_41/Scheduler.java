package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import javax.annotation.Nullable;

/* fires off scheduled mob events at time of day events. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Scheduler(
    /* The minimum the scheduler will be delayed. */
    @JsonProperty("min_delay_secs") @Nullable Double minDelaySecs,
    /* The maximum the scheduler will be delayed. */
    @JsonProperty("max_delay_secs") @Nullable Double maxDelaySecs,
    /* The list of triggers that fire when the conditions match the given filter criteria. If any filter criteria overlap the first defined event will be picked. */
    @JsonProperty("scheduled_events") @Nullable List<Object> scheduledEvents
) {
}
