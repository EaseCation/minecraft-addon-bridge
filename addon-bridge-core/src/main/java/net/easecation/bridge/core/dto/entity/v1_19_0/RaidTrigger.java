package net.easecation.bridge.core.dto.entity.v1_19_0;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Attempts to trigger a raid at the entity's location. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RaidTrigger(
    /* Event to run we attempt to trigger a raid on the village. */
    @JsonProperty("triggered_event") @Nullable Event triggeredEvent
) {
}
