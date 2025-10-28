package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows the a mob to become scared when the weather outside is thundering. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Scared(
    @JsonProperty("priority") @Nullable Priority priority,
    /* The interval in which a sound will play when active in a 1/delay chance to kick off. */
    @JsonProperty("sound_interval") @Nullable Integer soundInterval
) {
}
