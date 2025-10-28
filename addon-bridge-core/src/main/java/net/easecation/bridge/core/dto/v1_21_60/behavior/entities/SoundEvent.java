package net.easecation.bridge.core.dto.v1_21_60.behavior.entities;

import com.fasterxml.jackson.annotation.*;

/* Reference to a sound definition in "sound_definitions.json". */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SoundEvent(
    /* Reference to a sound definition in "sound_definitions.json". */
    String value
) {
}
