package net.easecation.bridge.core.dto.dialogue.v1_20_41;

import com.fasterxml.jackson.annotation.*;

/* A simple text component, will display the text raw (without processing). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Text(
    /* The text to display. */
    @JsonProperty("text") String text
) {
}
