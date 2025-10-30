package net.easecation.bridge.core.dto.spawn_rule.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* UNDOCUMENTED. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SpawnEvent(
    /* UNDOCUMENTED. */
    @JsonProperty("event") @Nullable String event
) {
}
