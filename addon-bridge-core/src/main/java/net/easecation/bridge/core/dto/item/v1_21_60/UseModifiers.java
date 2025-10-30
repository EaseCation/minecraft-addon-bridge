package net.easecation.bridge.core.dto.item.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* How long to use before item is done being used. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UseModifiers(
    /* Modifier value to scale the players movement speed when item is in use. */
    @JsonProperty("movement_modifier") @Nullable Double movementModifier,
    /* How long the item takes to use in seconds. */
    @JsonProperty("use_duration") Double useDuration
) {
}
