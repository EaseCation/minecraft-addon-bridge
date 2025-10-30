package net.easecation.bridge.core.dto.entity.v1_19_40;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sets the entity's base volume for sound effects. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record SoundVolume(
    /* The value of the volume the entity uses for sound effects. */
    @JsonProperty("value") @Nullable Double value
) {
}
