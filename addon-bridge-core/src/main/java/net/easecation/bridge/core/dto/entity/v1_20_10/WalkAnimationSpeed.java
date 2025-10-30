package net.easecation.bridge.core.dto.entity.v1_20_10;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Sets the speed multiplier for this entity's walk animation speed. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WalkAnimationSpeed(
    /* The higher the number, the faster the animation for walking plays. A value of 1.0 means normal speed, while 2.0 means twice as fast */
    @JsonProperty("value") @Nullable Double value
) {
}
