package net.easecation.bridge.core.dto.spawn_rule.v1_19_0;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum IbDifficulty {
    @JsonProperty("easy") EASY,
    @JsonProperty("normal") NORMAL,
    @JsonProperty("hard") HARD,
    @JsonProperty("peaceful") PEACEFUL
}
