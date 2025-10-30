package net.easecation.bridge.core.dto.dialogue.v1_20_41;

import com.fasterxml.jackson.annotation.*;

/* A json structure that allows for translations, or go from scores and selectors to text. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Rawtext(
    @JsonProperty("rawtext") Rawtext rawtext
) {
}
