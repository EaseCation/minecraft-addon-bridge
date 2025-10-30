package net.easecation.bridge.core.dto.dialogue.v1_21_60;

import com.fasterxml.jackson.annotation.*;

/* A text component that grabs the score from an given target and turns its value of a specified score. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Score(
    /* The score text component. */
    @JsonProperty("score") ScoreData score
) {
    
        /* The score text component. */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record ScoreData(
            /* A selector, player name (can be fake), or * to target who is reading the message. */
            @JsonProperty("name") String name,
            /* The scoreboard objective to retrieve the value of. */
            @JsonProperty("objective") String objective
        ) {
        }
}
