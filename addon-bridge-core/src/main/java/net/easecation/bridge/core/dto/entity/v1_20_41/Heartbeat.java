package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* defines the entity's heartbeat.. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Heartbeat(
    /* A Molang expression defining the inter-beat interval in seconds. A value of zero or less means no heartbeat.. */
    @JsonProperty("interval") @Nullable MolangNumber interval,
    /* Level sound event to be played as the heartbeat sound. */
    @JsonProperty("sound_event") @Nullable String soundEvent
) {
}
