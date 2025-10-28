package net.easecation.bridge.core.dto.v1_21_60.behavior.spawn_rules;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Event related to the spawning of an entity. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpawnEvent(
    /* UNDOCUMENTED. */
    @JsonProperty("event") @Nullable String event
) {
}
