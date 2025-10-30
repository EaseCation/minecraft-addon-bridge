package net.easecation.bridge.core.dto.entity.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Defines the entity's color. Only works on vanilla entities that have predefined color values (sheep, llama, shulker). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Color(
    /* The Palette Color value of the entity. */
    @JsonProperty("value") @Nullable Integer value
) {
}
