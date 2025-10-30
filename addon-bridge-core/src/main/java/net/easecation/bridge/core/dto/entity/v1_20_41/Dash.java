package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Ability for a ridable entity to dash. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dash(
    /* The dash cooldown in seconds. */
    @JsonProperty("cooldown_time") @Nullable Double cooldownTime,
    /* Horizontal momentum of the dash. */
    @JsonProperty("horizontal_momentum") @Nullable Double horizontalMomentum,
    /* Vertical momentum of the dash. */
    @JsonProperty("vertical_momentum") @Nullable Double verticalMomentum
) {
}
