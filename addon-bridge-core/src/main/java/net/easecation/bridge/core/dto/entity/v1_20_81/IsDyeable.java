package net.easecation.bridge.core.dto.entity.v1_20_81;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* Allows dyes to be used on this entity to change its color. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record IsDyeable(
    /* The text that will display when interacting with this entity with a dye when playing with Touch-screen controls. */
    @JsonProperty("interact_text") @Nullable String interactText
) {
}
