package net.easecation.bridge.core.dto.v1_21_60.behavior.items;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Used by record items to play music. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Record_(
    /* Signal strength for comparator blocks to use, from 1 - 13 */
    @JsonProperty("comparator_signal") @Nullable Double comparatorSignal,
    /* Duration of sound event in seconds, float value. */
    @JsonProperty("duration") @Nullable Double duration,
    /* Sound event type: 13, cat, blocks, chirp, far, mall, mellohi, stal, strad, ward, 11, wait, pigstep, otherside, 5, relic. */
    @JsonProperty("sound_event") String soundEvent
) {
}
