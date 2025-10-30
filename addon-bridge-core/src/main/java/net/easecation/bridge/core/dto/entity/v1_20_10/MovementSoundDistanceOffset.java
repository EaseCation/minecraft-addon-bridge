package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;

/* Sets the offset used to determine the next step distance for playing a movement sound. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record MovementSoundDistanceOffset(
    /* The higher the number, the less often the movement sound will be played. */
    @JsonProperty("value") Double value
) {
}
