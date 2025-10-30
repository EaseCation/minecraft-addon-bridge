package net.easecation.bridge.core.dto.dialogue.v1_20_41;

import com.fasterxml.jackson.annotation.*;

/* A text component that turns a selector into text, will usually display like: {@code Player1, Player2 and Player3}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Selector(
    /* The selector to target, for dialogue files, you can use \@initiator. */
    @JsonProperty("selector") String selector
) {
}
