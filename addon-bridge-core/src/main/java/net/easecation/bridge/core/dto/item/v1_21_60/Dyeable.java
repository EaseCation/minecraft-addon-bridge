package net.easecation.bridge.core.dto.item.v1_21_60;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Enables custom items to be dyed in cauldrons. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Dyeable(
    /* Color to use by default. If you do not want a default color you can leave the "default_color" off and the texture will be the same as if you did not have the component until it is dyed. */
    @JsonProperty("default_color") @Nullable Object defaultColor
) {
}
