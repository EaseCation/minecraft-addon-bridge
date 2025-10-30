package net.easecation.bridge.core.dto.dialogue.v1_20_41;

import com.fasterxml.jackson.annotation.*;
import javax.annotation.Nullable;

/* A text component that will attempt to translate the given key through the languages files. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Translate(
    /* The key to translate. */
    @JsonProperty("translate") String translate,
    @JsonProperty("with") @Nullable With with
) {
}
