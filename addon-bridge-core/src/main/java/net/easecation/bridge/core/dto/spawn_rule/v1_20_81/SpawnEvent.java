package net.easecation.bridge.core.dto.spawn_rule.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Event related to the spawning of an entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpawnEvent(
    /* UNDOCUMENTED. */
    @JsonProperty("event") @Nullable String event
) {
}
