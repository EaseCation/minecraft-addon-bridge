package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the entity's second texture color. Only works on vanilla entities that have a second predefined color values (tropical fish). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Color0(
    /* The second Palette Color value of the entity. */
    @JsonProperty("value") @Nullable Integer value
) {
}
